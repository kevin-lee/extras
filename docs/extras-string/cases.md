---
sidebar_position: 3
id: 'cases'
title: 'cases syntax'
---

## Import `cases` syntax

```scala mdoc
import extras.strings.syntax.cases._
```

Or

```scala
import extras.strings.syntax.all._
```

## Case Change for `String`

### `String` to `PascalCase`

```scala mdoc
"Abc".toPascalCase

"AbcDefGhi".toPascalCase

"abcDefGhi".toPascalCase

"abc".toPascalCase

"ABC".toPascalCase

"Abc_Def_Ghi".toPascalCase

"abc_def_ghi".toPascalCase

"abc__def__ghi".toPascalCase

"Abc-Def-Ghi".toPascalCase

"abc-def-ghi".toPascalCase

"abc--def--ghi".toPascalCase

"Abc Def Ghi".toPascalCase

"abc def ghi".toPascalCase

"AbcDef_Ghi_jkl-Mno-pqr Stu vwx".toPascalCase

"AbcDef__Ghi___jkl--Mno---pqr  Stu   vwx".toPascalCase
```

### `String` to one `PascalCase`

```scala mdoc
"Abc".toOnePascalCase

"AbcDefGhi".toOnePascalCase

"abcDefGhi".toOnePascalCase

"abc".toOnePascalCase

"ABC".toOnePascalCase

"Abc_Def_Ghi".toOnePascalCase

"abc_def_ghi".toOnePascalCase

"abc__def__ghi".toOnePascalCase

"Abc-Def-Ghi".toOnePascalCase

"abc-def-ghi".toOnePascalCase

"abc--def--ghi".toOnePascalCase

"Abc Def Ghi".toOnePascalCase

"abc def ghi".toOnePascalCase

"AbcDef_Ghi_jkl-Mno-pqr Stu vwx".toOnePascalCase

"AbcDef__Ghi___jkl--Mno---pqr  Stu   vwx".toOnePascalCase
```

### `String` to `camelCase`

```scala mdoc
"Abc".toCamelCase

"AbcDefGhi".toCamelCase

"abcDefGhi".toCamelCase

"abc".toCamelCase

"ABC".toCamelCase

"Abc_Def_Ghi".toCamelCase

"abc_def_ghi".toCamelCase

"abc__def__ghi".toCamelCase

"Abc-Def-Ghi".toCamelCase

"abc-def-ghi".toCamelCase

"abc--def--ghi".toCamelCase

"Abc Def Ghi".toCamelCase

"abc def ghi".toCamelCase

"AbcDef_Ghi_jkl-Mno-pqr Stu vwx".toCamelCase

"AbcDef__Ghi___jkl--Mno---pqr  Stu   vwx".toCamelCase
```

### `String` to `Snake_Case`

```scala mdoc
"Abc".toSnakeCase

"AbcDefGhi".toSnakeCase

"abcDefGhi".toSnakeCase

"abc".toSnakeCase

"ABC".toSnakeCase

"Abc_Def_Ghi".toSnakeCase

"abc_def_ghi".toSnakeCase

"ABC_DEF_GHI".toSnakeCase

"abc-def-ghi".toSnakeCase

"Abc-Def-Ghi".toSnakeCase

"ABC-DEF-GHI".toSnakeCase

"abc def ghi".toSnakeCase

"Abc Def Ghi".toSnakeCase

"ABC DEF GHI".toSnakeCase
```

### `String` to `SNAKE_UPPER_CASE`

```scala mdoc
"Abc".toSnakeUpperCase

"AbcDefGhi".toSnakeUpperCase

"abcDefGhi".toSnakeUpperCase

"abc".toSnakeUpperCase

"ABC".toSnakeUpperCase

"Abc_Def_Ghi".toSnakeUpperCase

"abc_def_ghi".toSnakeUpperCase

"ABC_DEF_GHI".toSnakeUpperCase

"abc-def-ghi".toSnakeUpperCase

"Abc-Def-Ghi".toSnakeUpperCase

"ABC-DEF-GHI".toSnakeUpperCase

"abc def ghi".toSnakeUpperCase

"Abc Def Ghi".toSnakeUpperCase

"ABC DEF GHI".toSnakeUpperCase
```

### `String` to `snake_lower_case`

```scala mdoc
"Abc".toSnakeLowerCase

"AbcDefGhi".toSnakeLowerCase

"abcDefGhi".toSnakeLowerCase

"abc".toSnakeLowerCase

"ABC".toSnakeLowerCase

"Abc_Def_Ghi".toSnakeLowerCase

"abc_def_ghi".toSnakeLowerCase

"ABC_DEF_GHI".toSnakeLowerCase

"abc-def-ghi".toSnakeLowerCase

"Abc-Def-Ghi".toSnakeLowerCase

"ABC-DEF-GHI".toSnakeLowerCase

"abc def ghi".toSnakeLowerCase

"Abc Def Ghi".toSnakeLowerCase

"ABC DEF GHI".toSnakeLowerCase
```

### `String` to `Kebab-Case`

```scala mdoc
"Abc".toKebabCase

"AbcDefGhi".toKebabCase

"abcDefGhi".toKebabCase

"abc".toKebabCase

"ABC".toKebabCase

"Abc_Def_Ghi".toKebabCase

"abc_def_ghi".toKebabCase

"ABC_DEF_GHI".toKebabCase

"abc-def-ghi".toKebabCase

"Abc-Def-Ghi".toKebabCase

"ABC-DEF-GHI".toKebabCase

"abc def ghi".toKebabCase

"Abc Def Ghi".toKebabCase

"ABC DEF GHI".toKebabCase
```

### `String` to `KEBAB-UPPER-CASE`

```scala mdoc
"Abc".toKebabUpperCase

"AbcDefGhi".toKebabUpperCase

"abcDefGhi".toKebabUpperCase

"abc".toKebabUpperCase

"ABC".toKebabUpperCase

"Abc_Def_Ghi".toKebabUpperCase

"abc_def_ghi".toKebabUpperCase

"ABC_DEF_GHI".toKebabUpperCase

"abc-def-ghi".toKebabUpperCase

"Abc-Def-Ghi".toKebabUpperCase

"ABC-DEF-GHI".toKebabUpperCase

"abc def ghi".toKebabUpperCase

"Abc Def Ghi".toKebabUpperCase

"ABC DEF GHI".toKebabUpperCase
```

### `String` to `kebab-lower-case`

```scala mdoc
"Abc".toKebabLowerCase

"AbcDefGhi".toKebabLowerCase

"abcDefGhi".toKebabLowerCase

"abc".toKebabLowerCase

"ABC".toKebabLowerCase

"Abc_Def_Ghi".toKebabLowerCase

"abc_def_ghi".toKebabLowerCase

"ABC_DEF_GHI".toKebabLowerCase

"abc-def-ghi".toKebabLowerCase

"Abc-Def-Ghi".toKebabLowerCase

"ABC-DEF-GHI".toKebabLowerCase

"abc def ghi".toKebabLowerCase

"Abc Def Ghi".toKebabLowerCase

"ABC DEF GHI".toKebabLowerCase
```

## Case Change for `Seq[String]`

### `Seq[String]` to `PascalCaseString`

```scala mdoc
List("Abc", "Def").mkPascalCaseString

List("AbcDef", "Ghi", "jkl", "MnoPqr").mkPascalCaseString

List("abcDef", "Ghi", "jkl", "MnoPqr").mkPascalCaseString

List("abc").mkPascalCaseString

List("ABC").mkPascalCaseString

List("Abc_Def_Ghi", "jkl_mno_Pqr").mkPascalCaseString

List("abc_def_ghi", "jkl_mno_pqr", "st_u").mkPascalCaseString

List("ABC_DEF_GHI", "JKL_MNO_PQR", "ST_U").mkPascalCaseString

List("Abc-Def-Ghi", "jkl-mno-Pqr").mkPascalCaseString

List("abc-def-ghi", "jkl-mno-pqr", "st-u").mkPascalCaseString

List("ABC-DEF-GHI", "JKL-MNO-PQR", "ST-U").mkPascalCaseString

List("Abc Def Ghi", "jkl mno Pqr").mkPascalCaseString

List("abc def ghi", "jkl mno pqr", "st u").mkPascalCaseString

List("ABC DEF GHI", "JKL MNO PQR", "ST U").mkPascalCaseString
```

### `Seq[String]` to `camelCaseString`

```scala mdoc
List("Abc", "Def").mkCamelCaseString

List("AbcDef", "Ghi", "jkl", "MnoPqr").mkCamelCaseString

List("abcDef", "Ghi", "jkl", "MnoPqr").mkCamelCaseString

List("abc").mkCamelCaseString

List("ABC").mkCamelCaseString

List("Abc_Def_Ghi", "jkl_mno_Pqr").mkCamelCaseString

List("abc_def_ghi", "jkl_mno_pqr", "st_u").mkCamelCaseString

List("ABC_DEF_GHI", "JKL_MNO_PQR", "ST_U").mkCamelCaseString

List("Abc-Def-Ghi", "jkl-mno-Pqr").mkCamelCaseString

List("abc-def-ghi", "jkl-mno-pqr", "st-u").mkCamelCaseString

List("ABC-DEF-GHI", "JKL-MNO-PQR", "ST-U").mkCamelCaseString

List("Abc Def Ghi", "jkl mno Pqr").mkCamelCaseString

List("abc def ghi", "jkl mno pqr", "st u").mkCamelCaseString

List("ABC DEF GHI", "JKL MNO PQR", "ST U").mkCamelCaseString
```

### `Seq[String]` to `Snake_Case_String`

```scala mdoc
List("Abc", "Def").mkSnakeCaseString

List("AbcDef", "Ghi", "jkl", "MnoPqr").mkSnakeCaseString

List("abcDef", "Ghi", "jkl", "MnoPqr").mkSnakeCaseString

List("abc").mkSnakeCaseString

List("ABC").mkSnakeCaseString

List("Abc_Def_Ghi", "jkl_mno_Pqr").mkSnakeCaseString

List("abc_def_ghi", "jkl_mno_pqr", "st_u").mkSnakeCaseString

List("ABC_DEF_GHI", "JKL_MNO_PQR", "ST_U").mkSnakeCaseString

List("Abc-Def-Ghi", "jkl-mno-Pqr").mkSnakeCaseString

List("abc-def-ghi", "jkl-mno-pqr", "st-u").mkSnakeCaseString

List("ABC-DEF-GHI", "JKL-MNO-PQR", "ST-U").mkSnakeCaseString

List("Abc Def Ghi", "jkl mno Pqr").mkSnakeCaseString

List("abc def ghi", "jkl mno pqr", "st u").mkSnakeCaseString

List("ABC DEF GHI", "JKL MNO PQR", "ST U").mkSnakeCaseString
```

### `Seq[String]` to `SNAKE_UPPER_CASE_STRING`

```scala mdoc
List("Abc", "Def").mkSnakeUpperCaseString

List("AbcDef", "Ghi", "jkl", "MnoPqr").mkSnakeUpperCaseString

List("abcDef", "Ghi", "jkl", "MnoPqr").mkSnakeUpperCaseString

List("abc").mkSnakeUpperCaseString

List("ABC").mkSnakeUpperCaseString

List("Abc_Def_Ghi", "jkl_mno_Pqr").mkSnakeUpperCaseString

List("abc_def_ghi", "jkl_mno_pqr", "st_u").mkSnakeUpperCaseString

List("ABC_DEF_GHI", "JKL_MNO_PQR", "ST_U").mkSnakeUpperCaseString

List("Abc-Def-Ghi", "jkl-mno-Pqr").mkSnakeUpperCaseString

List("abc-def-ghi", "jkl-mno-pqr", "st-u").mkSnakeUpperCaseString

List("ABC-DEF-GHI", "JKL-MNO-PQR", "ST-U").mkSnakeUpperCaseString

List("Abc Def Ghi", "jkl mno Pqr").mkSnakeUpperCaseString

List("abc def ghi", "jkl mno pqr", "st u").mkSnakeUpperCaseString

List("ABC DEF GHI", "JKL MNO PQR", "ST U").mkSnakeUpperCaseString
```

### `Seq[String]` to `snake_lower_case_string`

```scala mdoc
List("Abc", "Def").mkSnakeLowerCaseString

List("AbcDef", "Ghi", "jkl", "MnoPqr").mkSnakeLowerCaseString

List("abcDef", "Ghi", "jkl", "MnoPqr").mkSnakeLowerCaseString

List("abc").mkSnakeLowerCaseString

List("ABC").mkSnakeLowerCaseString

List("Abc_Def_Ghi", "jkl_mno_Pqr").mkSnakeLowerCaseString

List("abc_def_ghi", "jkl_mno_pqr", "st_u").mkSnakeLowerCaseString

List("ABC_DEF_GHI", "JKL_MNO_PQR", "ST_U").mkSnakeLowerCaseString

List("Abc-Def-Ghi", "jkl-mno-Pqr").mkSnakeLowerCaseString

List("abc-def-ghi", "jkl-mno-pqr", "st-u").mkSnakeLowerCaseString

List("ABC-DEF-GHI", "JKL-MNO-PQR", "ST-U").mkSnakeLowerCaseString

List("Abc Def Ghi", "jkl mno Pqr").mkSnakeLowerCaseString

List("abc def ghi", "jkl mno pqr", "st u").mkSnakeLowerCaseString

List("ABC DEF GHI", "JKL MNO PQR", "ST U").mkSnakeLowerCaseString
```

### `Seq[String]` to `Kebab-Case-String`

```scala mdoc
List("Abc", "Def").mkKebabCaseString

List("AbcDef", "Ghi", "jkl", "MnoPqr").mkKebabCaseString

List("abcDef", "Ghi", "jkl", "MnoPqr").mkKebabCaseString

List("abc").mkKebabCaseString

List("ABC").mkKebabCaseString

List("Abc_Def_Ghi", "jkl_mno_Pqr").mkKebabCaseString

List("abc_def_ghi", "jkl_mno_pqr", "st_u").mkKebabCaseString

List("ABC_DEF_GHI", "JKL_MNO_PQR", "ST_U").mkKebabCaseString

List("Abc-Def-Ghi", "jkl-mno-Pqr").mkKebabCaseString

List("abc-def-ghi", "jkl-mno-pqr", "st-u").mkKebabCaseString

List("ABC-DEF-GHI", "JKL-MNO-PQR", "ST-U").mkKebabCaseString

List("Abc Def Ghi", "jkl mno Pqr").mkKebabCaseString

List("abc def ghi", "jkl mno pqr", "st u").mkKebabCaseString

List("ABC DEF GHI", "JKL MNO PQR", "ST U").mkKebabCaseString
```

