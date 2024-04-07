package com.weberhsu.data.database.mapper

import com.weberhsu.base.Mapper
import com.weberhsu.data.FiatDataModel
import com.weberhsu.domain.entity.CurrencyInfo
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class CurrencyInfoForFiatMapper @Inject constructor() : Mapper<FiatDataModel, CurrencyInfo> {
    override fun from(i: FiatDataModel): CurrencyInfo {
        return CurrencyInfo(
            id = i.id,
            name = i.name,
            symbol = i.symbol,
            code = i.code
        )
    }

    override fun to(o: CurrencyInfo): FiatDataModel {
        return FiatDataModel(
            id = o.id,
            name = o.name,
            symbol = o.symbol ?: "",
            code = o.code
        )
    }

}