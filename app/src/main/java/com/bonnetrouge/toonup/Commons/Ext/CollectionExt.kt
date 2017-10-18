package com.bonnetrouge.toonup.Commons.Ext

import java.util.regex.Pattern

/**
 * Returns a list containing only elements where an elements [filterProperty] fuzzy matches [stringMatch]
 */
inline fun <T> Iterable<T>.fuzzyFilter(fuzziness: Int = 1, stringMatch: CharSequence, filterProperty: T.() -> String): List<T> {
    val lazySearchPattern = getFuzzinessPattern(fuzziness, stringMatch)
    val filteredList = mutableListOf<T>()
    this.forEach {
        val lazySearchMatcher = lazySearchPattern.matcher(it.filterProperty())
        if (lazySearchMatcher.find()) {
            filteredList.add(it)
        }
    }
    return filteredList
}

/**
 * Returns a fuzzy pattern with a degree of [fuzziness]
 */
fun getFuzzinessPattern(fuzziness: Int, stringMatch: CharSequence): Pattern {
    when (fuzziness) {
        1 -> return buildFuzziness1Pattern(stringMatch)
        else -> return buildFuzziness1Pattern(stringMatch)
    }
}

/**
 * Builds a pattern for [stringMatch] that has a fuzziness factor of 1
 */
fun buildFuzziness1Pattern(stringMatch: CharSequence): Pattern {
    val lazySearchRegexBuilder = StringBuilder()
    lazySearchRegexBuilder.append("^(")
    stringMatch.forEach {
        lazySearchRegexBuilder.append(it)
        lazySearchRegexBuilder.append(".*")
    }
    lazySearchRegexBuilder.append(")")
    return Pattern.compile(lazySearchRegexBuilder.toString(), Pattern.CASE_INSENSITIVE)
}