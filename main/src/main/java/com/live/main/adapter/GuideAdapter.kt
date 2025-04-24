package com.live.main.adapter

import com.james.main.R
import com.live.main.entity.GuideBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @Author: james
 * @Date: 2024/4/12 09:51
 * @Description:
 */
class GuideAdapter : BaseBannerAdapter<GuideBean>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.layout_guide_item

    override fun bindData(
        holder: BaseViewHolder<GuideBean>,
        data: GuideBean,
        position: Int,
        pageSize: Int
    ) {
        holder.setImageResource(R.id.banner, data.image)
    }
}