package com.weberhsu.data.database.mapper

import com.weberhsu.base.Mapper
import com.weberhsu.data.FiatDataModel
import com.weberhsu.domain.entity.FiatEntity
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class FiatDataEntityMapper @Inject constructor() : Mapper<FiatDataModel, FiatEntity> {
    override fun from(i: FiatDataModel): FiatEntity {
        return FiatEntity(
            id = i.id,
            name = i.name,
            symbol = i.symbol,
            code = i.code
        )
    }

    override fun to(o: FiatEntity): FiatDataModel {
        return FiatDataModel(
            id = o.id,
            name = o.name,
            symbol = o.symbol,
            code = o.code
        )
    }
}