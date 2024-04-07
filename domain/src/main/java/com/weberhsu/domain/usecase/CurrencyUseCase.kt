package com.weberhsu.domain.usecase

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.weberhsu.base.dispatchers.DispatcherProvider
import com.weberhsu.domain.entity.CryptoEntity
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.domain.entity.FiatEntity
import com.weberhsu.domain.repository.CryptoRepository
import com.weberhsu.domain.repository.FiatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class CurrencyUseCase @Inject constructor(
    private val fiatRepo: FiatRepository,
    private val cryptoRepo: CryptoRepository,
    private val dispatchers: DispatcherProvider
) : BaseUseCase {

    private fun getInitialCryptoDataString(): String {
        return "[\n" +
                "\t{\n" +
                "\t\t\"id\": \"BTC\", \"name\": \"Bitcoin\", \"symbol\": \"BTC\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"ETH\", \"name\": \"Ethereum\", \"symbol\": \"ETH\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"XRP\", \"name\": \"XRP\", \"symbol\": \"XRP\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"BCH\", \"name\": \"Bitcoin Cash\", \"symbol\": \"BCH\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"LTC\", \"name\": \"Litecoin\", \"symbol\": \"LTC\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"EOS\", \"name\": \"EOS\", \"symbol\": \"EOS\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"BNB\",  \"name\": \"Binance Coin\", \"symbol\": \"BNB\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"LINK\", \"name\": \"Chainlink\", \"symbol\": \"LINK\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"NEO\", \"name\": \"NEO\", \"symbol\": \"NEO\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"ETC\", \"name\": \"Ethereum Classic\", \"symbol\": \"ETC\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"ONT\", \"name\": \"Ontology\", \"symbol\": \"ONT\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"CRO\", \"name\": \"Crypto.com Chain\", \"symbol\": \"CRO\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"CUC\", \"name\": \"Cucumber\", \"symbol\": \"CUC\"\n" +
                "\t}, \n" +
                "\t{\n" +
                "\t\t\"id\": \"USDC\", \"name\": \"USD Coin\", \"symbol\": \"USDC\"\n" +
                "\t}\n" +
                "]"
    }

    private fun getInitialFiatDataString(): String {
        return "[\n" +
                "\t{\n" +
                "\t\t\"id\": \"SGD\", \"name\": \"Singapore Dollar\", \"symbol\": \"\$\", \"code\": \"SGD\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"EUR\", \"name\": \"Euro\", \"symbol\": \"€\", \"code\": \"EUR\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"GBP\", \"name\": \"British Pound\", \"symbol\": \"£\", \"code\": \"GBP\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"HKD\", \"name\": \"Hong Kong Dollar\", \"symbol\": \"\$\", \"code\": \"HKD\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"JPY\", \"name\": \"Japanese Yen\", \"symbol\": \"¥\", \"code\": \"JPY\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"AUD\", \"name\": \"Australian Dollar\", \"symbol\": \"\$\", \"code\": \"AUD\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"id\": \"USD\", \"name\": \"United States Dollar\", \"symbol\": \"\$\", \"code\": \"USD\"\n" +
                "\t}\n" +
                "]"
    }

    suspend fun clearData(): Result<Unit> {
        return Result.runCatching {
            cryptoRepo.clear().onFailure {
                throw it
            }
            fiatRepo.clear().onFailure {
                throw it
            }
        }
    }

    suspend fun insertInitialData(): Result<Unit> {
        return Result.runCatching {
            cryptoRepo.insertCryptos(
                (Gson().fromJson<List<CryptoEntity>>(
                    getInitialCryptoDataString(),
                    object : TypeToken<List<CryptoEntity>>() {}.type
                )).map {
                    it.toCurrencyInfo()
                }
            ).onFailure {
                throw it
            }
            fiatRepo.insertFiats(
                (Gson().fromJson<List<FiatEntity>>(
                    getInitialFiatDataString(),
                    object : TypeToken<List<FiatEntity>>() {}.type
                )).map {
                    it.toCurrencyInfo()
                }
            ).onFailure {
                throw it
            }
        }
    }

    fun searchFiatCurrencies(query: String): Flow<Result<List<CurrencyInfo>>> {
        return fiatRepo.search(query)
            .catch {
                Result.failure<Exception>(it)
            }.map {
                Result.success(it)
            }.distinctUntilChanged()
    }

    fun searchCryptoCurrencies(query: String): Flow<Result<List<CurrencyInfo>>> {
        return cryptoRepo.search(query)
            .catch {
                Result.failure<Exception>(it)
            }.map {
                Result.success(it)
            }.distinctUntilChanged()
    }

    fun getFiatCurrencies(): Flow<Result<List<CurrencyInfo>>> {
        return fiatRepo.getAllFiats()
            .catch {
                Result.failure<Exception>(it)
            }.map {
                Result.success(it)
            }.distinctUntilChanged()
    }

    fun getCryptoCurrencies(): Flow<Result<List<CurrencyInfo>>> {
        return cryptoRepo.getAllCryptos()
            .catch {
                Result.failure<Exception>(it)
            }.map {
                Result.success(it)
            }.distinctUntilChanged()
    }
}