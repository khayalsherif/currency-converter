package az.khayalsharifli.bankrespublika.data.mapper

import az.khayalsharifli.bankrespublika.data.remote.RemoteDtoItem
import az.khayalsharifli.bankrespublika.data.local.LocalDtoItem

/***
 * I set the elvis operator everywhere,
 * because a lot of data comes mixed null inside. :(
 */
class MoneyMapper : Mapper<RemoteDtoItem, LocalDtoItem> {
    override fun fromRemoteToLocal(remote: RemoteDtoItem): LocalDtoItem {
        // Eur
        val eurBuy = remote.cash?.eur?.buy ?: "0.0"
        val eurSell = remote.cash?.eur?.sell ?: "0.0"
        val eurCode = remote.cash?.eur?.code ?: "0.0"

        //Rub
        val rubBuy = remote.cash?.rub?.buy ?: "0.0"
        val rubSell = remote.cash?.rub?.sell ?: "0.0"
        val rubCode = remote.cash?.rub?.code ?: "0.0"

        //Usd
        val usdBuy = remote.cash?.usd?.buy ?: "0.0"
        val usdSell = remote.cash?.usd?.sell ?: "0.0"
        val usdCode = remote.cash?.usd?.code ?: "0.0"

        val cash = LocalDtoItem.Cash(
            eur = LocalDtoItem.Cash.Eur(eurBuy, eurCode, eurSell),
            rub = LocalDtoItem.Cash.Rub(rubBuy, rubCode, rubSell),
            usd = LocalDtoItem.Cash.Usd(usdBuy, usdCode, usdSell)
        )
        return LocalDtoItem(cash = cash, update_time = remote.update_time!!, name = remote.name!!)
    }
}