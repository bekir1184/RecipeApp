package com.example.recipeapp

import com.beust.klaxon.*

private fun <T> Klaxon.convert(k: kotlin.reflect.KClass<*>, fromJson: (JsonValue) -> T, toJson: (T) -> String, isUnion: Boolean = false) =
        this.converter(object: Converter {
            @Suppress("UNCHECKED_CAST")
            override fun toJson(value: Any)        = toJson(value as T)
            override fun fromJson(jv: JsonValue)   = fromJson(jv) as Any
            override fun canConvert(cls: Class<*>) = cls == k.java || (isUnion && cls.superclass == k.java)
        })

private val klaxon = Klaxon()
        .convert(Unit::class, { Unit.fromValue(it.string!!) }, { "\"${it.value}\"" })

data class Model (
        val q: String,
        val from: Long,
        val to: Long,
        val more: Boolean,
        val count: Long,
        val hits: List<Hit>
) {
    public fun toJson() = klaxon.toJsonString(this)

    companion object {
        public fun fromJson(json: String) = klaxon.parse<Model>(json)
    }
}

data class Hit (
        val recipe: Recipe,
        val bookmarked: Boolean,
        val bought: Boolean
)

data class Recipe (
        val uri: String,
        val label: String,
        val image: String,
        val source: String,
        val url: String,
        val shareAs: String,
        val yield: Double,
        val dietLabels: List<Any?>,
        val healthLabels: List<String>,
        val cautions: List<String>,
        val ingredientLines: List<String>,
        val ingredients: List<Ingredient>,
        val calories: Double,
        val totalWeight: Double,
        val totalTime: Double,
        val totalNutrients: Map<String, Total>,
        val totalDaily: Map<String, Total>,
        val digest: List<Digest>
)

data class Digest (
        val label: String,
        val tag: String,
        val schemaOrgTag: String? = null,
        val total: Double,
        val hasRDI: Boolean,
        val daily: Double,
        val unit: Unit,
        val sub: List<Digest>? = null
)

enum class Unit(val value: String) {
    Empty("%"),
    G("g"),
    Kcal("kcal"),
    Mg("mg"),
    Μg("µg");

    companion object {
        public fun fromValue(value: String): Unit = when (value) {
            "%"    -> Empty
            "g"    -> G
            "kcal" -> Kcal
            "mg"   -> Mg
            "µg"   -> Μg
            else   -> throw IllegalArgumentException()
        }
    }
}

data class Ingredient (
        val text: String,
        val weight: Double,
        val image: String? = null
)

data class Total (
        val label: String,
        val quantity: Double,
        val unit: Unit
)