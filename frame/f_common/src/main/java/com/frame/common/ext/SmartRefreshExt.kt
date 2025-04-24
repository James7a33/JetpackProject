package com.frame.common.ext

import android.content.Context
import android.view.LayoutInflater
import com.chad.library.adapter4.BaseQuickAdapter
import com.frame.base.databinding.LayoutBaseEmptyBinding
import com.frame.base.loading.LoadStatusEntity
import com.frame.net.base.BasePage
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tools.toast.ext.toastShort

/**
 * Author: james
 * Created: 2024/8/27 16:41
 * Description: 下拉刷新，上拉加载
 */

/**
 * 下拉刷新
 * @receiver SmartRefreshLayout
 * @param refreshAction Function0<Unit>
 * @return SmartRefreshLayout
 */
fun SmartRefreshLayout.refresh(refreshAction: () -> Unit = {}): SmartRefreshLayout {
    this.setOnRefreshListener {
        refreshAction.invoke()
    }
    return this
}

/**
 * 上拉加载
 * @receiver SmartRefreshLayout
 * @param loadMoreAction Function0<Unit>
 * @return SmartRefreshLayout
 */
fun SmartRefreshLayout.loadMore(loadMoreAction: () -> Unit = {}): SmartRefreshLayout {
    this.setOnLoadMoreListener {
        loadMoreAction.invoke()
    }
    return this
}

/**
 * 列表数据加载成功
 * @receiver BaseQuickAdapter<T,*>
 * @param baseListNetEntity BaseListNetEntity<T>
 */
fun <T : Any> BaseQuickAdapter<T, *>.loadListSuccess(baseListNetEntity: BasePage<T>, smartRefreshLayout: SmartRefreshLayout) {
    //关闭头部刷新
    if (baseListNetEntity.isRefresh()) {
        //如果是第一页 那么设置最新数据替换
        this.submitList(baseListNetEntity.getPageData()?: arrayListOf())
        smartRefreshLayout.finishRefresh()
    } else {
        //不是第一页，累加数据
        this.addAll(baseListNetEntity.getPageData())
        //刷新一下分割线
        this.recyclerView.post { this.recyclerView.invalidateItemDecorations() }
    }
    //如果还有下一页数据 那么设置 smartRefreshLayout 还可以加载更多数据
    if (baseListNetEntity.hasMore() && !baseListNetEntity.isEmpty()) {
        smartRefreshLayout.finishLoadMore()
        smartRefreshLayout.setNoMoreData(false)
    } else {
        //如果没有更多数据了，设置 smartRefreshLayout 加载完毕 没有更多数据
        smartRefreshLayout.finishLoadMoreWithNoMoreData()
    }
}

/**
 * 列表数据请求失败
 * @receiver BaseQuickAdapter<*, *>
 * @param loadStatus LoadStatusEntity
 * @param smartRefreshLayout SmartRefreshLayout
 */
fun BaseQuickAdapter<*, *>.loadListError(loadStatus: LoadStatusEntity, smartRefreshLayout: SmartRefreshLayout) {
    if (loadStatus.isRefresh) {
        //关闭头部刷新
        smartRefreshLayout.finishRefresh()
    } else {
        // 不是第一页请求，让recyclerview设置加载失败
        smartRefreshLayout.finishLoadMore(false)
    }
    //给个错误提示
    loadStatus.errorMessage.toastShort()
}

/**
 * empty
 */
fun BaseQuickAdapter<*,*>.empty(context: Context){
    isStateViewEnable = true
    stateView = LayoutBaseEmptyBinding.inflate(LayoutInflater.from(context)).root
}
