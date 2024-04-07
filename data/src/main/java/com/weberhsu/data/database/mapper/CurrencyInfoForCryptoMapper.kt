package com.weberhsu.data.database.mapper

import com.weberhsu.base.Mapper
import com.weberhsu.data.CryptoDataModel
import com.weberhsu.domain.entity.CurrencyInfo
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class CurrencyInfoForCryptoMapper @Inject constructor() : Mapper<CryptoDataModel, CurrencyInfo> {
    override fun from(i: CryptoDataModel): CurrencyInfo {
        return CurrencyInfo(
            id = i.id,
            name = i.name,
            code = i.symbol
        )
    }

    override fun to(o: CurrencyInfo): CryptoDataModel {
        return CryptoDataModel(
            id = o.id,
            name = o.name,
            symbol = o.code
        )
    }

}