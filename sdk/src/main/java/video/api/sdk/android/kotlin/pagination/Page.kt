package video.api.sdk.android.kotlin.pagination

import org.json.JSONObject
import video.api.sdk.android.kotlin.model.BodyTransformer
import video.api.sdk.android.kotlin.model.iterator


class Page<T>(
    val body: JSONObject,
    private val transformer: BodyTransformer<T>
) {
    fun items(): List<T> {
        val jsonItems = body.getJSONArray("data")

        val items = arrayListOf<T>()
        jsonItems.iterator<JSONObject>().forEach {
            items.add(transformer.fromJson(it))
        }

        return items
    }

    private fun pagination(): JSONObject = body.getJSONObject("pagination")

    fun number(): Int = pagination().getInt("currentPage")
    fun itemCount(): Int = pagination().getInt("currentPageItems")
    fun requestedItemCount(): Int = pagination().getInt("pageSize")
    fun pagesTotal(): Int = pagination().getInt("pagesTotal")
    fun itemsTotal(): Int = pagination().getInt("itemsTotal")

    fun hasNext(): Boolean = number() < pagesTotal()
}