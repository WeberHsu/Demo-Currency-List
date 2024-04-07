package com.weberhsu.data.database.mapper

import com.weberhsu.base.Mapper
import com.weberhsu.data.CryptoDataModel
import com.weberhsu.domain.entity.CryptoEntity
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class CryptoDataEntityMapper @Inject constructor() : Mapper<CryptoDataModel, CryptoEntity> {
    override fun from(i: CryptoDataModel): CryptoEntity {
        return CryptoEntity(
            id = i.id,
            name = i.name,
            symbol = i.symbol
        )
    }

    override fun to(o: CryptoEntity): CryptoDataModel {
        return CryptoDataModel(
            id = o.id,
            name = o.name,
            symbol = o.symbol
        )
    }
}