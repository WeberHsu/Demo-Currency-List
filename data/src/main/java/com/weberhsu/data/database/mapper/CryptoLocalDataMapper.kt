package com.weberhsu.data.database.mapper

import com.weberhsu.base.Mapper
import com.weberhsu.data.CryptoDataModel
import com.weberhsu.data.database.model.CryptoLocalModel
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class CryptoLocalDataMapper @Inject constructor() : Mapper<CryptoLocalModel, CryptoDataModel> {
    override fun from(i: CryptoLocalModel): CryptoDataModel {
        return CryptoDataModel(
            id = i.id,
            name = i.name,
            symbol = i.symbol,
        )
    }

    override fun to(o: CryptoDataModel): CryptoLocalModel {
        return CryptoLocalModel(
            id = o.id,
            name = o.name,
            symbol = o.symbol,
        )
    }
}