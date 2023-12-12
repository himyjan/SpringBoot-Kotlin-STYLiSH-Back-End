package com.example.backend

import java.util.*

fun String.toSlug() = lowercase(Locale.getDefault())
  .replace("[^a-z\\d\\s]".toRegex(), " ")
  .split("\\s+".toRegex())
  .joinToString("-")
  .replace("-+".toRegex(), "-")