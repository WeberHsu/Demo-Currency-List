package com.weberhsu.data.database.mapper

import com.weberhsu.base.Mapper
import com.weberhsu.data.FiatDataModel
import com.weberhsu.data.database.model.FiatLocalModel
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class FiatLocalDataMapper @Inject constructor() : Mapper<FiatLocalModel, FiatDataModel> {
    override fun from(i: FiatLocalModel): FiatDataModel {
        return FiatDataModel(
            id = i.id,
            name = i.name,
            symbol = i.symbol,
            code = i.code
        )
    }

    override fun to(o: FiatDataModel): FiatLocalModel {
        return FiatLocalModel(
            id = o.id,
            name = o.name,
            symbol = o.symbol,
            code = o.code
        )
    }
}