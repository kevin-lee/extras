"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[902],{3905:function(e,n,t){t.d(n,{Zo:function(){return d},kt:function(){return u}});var a=t(7294);function i(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function r(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);n&&(a=a.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,a)}return t}function l(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?r(Object(t),!0).forEach((function(n){i(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):r(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function s(e,n){if(null==e)return{};var t,a,i=function(e,n){if(null==e)return{};var t,a,i={},r=Object.keys(e);for(a=0;a<r.length;a++)t=r[a],n.indexOf(t)>=0||(i[t]=e[t]);return i}(e,n);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(a=0;a<r.length;a++)t=r[a],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(i[t]=e[t])}return i}var p=a.createContext({}),o=function(e){var n=a.useContext(p),t=n;return e&&(t="function"==typeof e?e(n):l(l({},n),e)),t},d=function(e){var n=o(e.components);return a.createElement(p.Provider,{value:n},e.children)},m={inlineCode:"code",wrapper:function(e){var n=e.children;return a.createElement(a.Fragment,{},n)}},c=a.forwardRef((function(e,n){var t=e.components,i=e.mdxType,r=e.originalType,p=e.parentName,d=s(e,["components","mdxType","originalType","parentName"]),c=o(t),u=i,v=c["".concat(p,".").concat(u)]||c[u]||m[u]||r;return t?a.createElement(v,l(l({ref:n},d),{},{components:t})):a.createElement(v,l({ref:n},d))}));function u(e,n){var t=arguments,i=n&&n.mdxType;if("string"==typeof e||i){var r=t.length,l=new Array(r);l[0]=c;var s={};for(var p in n)hasOwnProperty.call(n,p)&&(s[p]=n[p]);s.originalType=e,s.mdxType="string"==typeof e?e:i,l[1]=s;for(var o=2;o<r;o++)l[o]=t[o];return a.createElement.apply(null,l)}return a.createElement.apply(null,t)}c.displayName="MDXCreateElement"},2626:function(e,n,t){t.r(n),t.d(n,{frontMatter:function(){return s},contentTitle:function(){return p},metadata:function(){return o},toc:function(){return d},default:function(){return c}});var a=t(7462),i=t(3366),r=(t(7294),t(3905)),l=["components"],s={sidebar_position:2,id:"syntax",title:"Refinement Syntax"},p=void 0,o={unversionedId:"extras-refinement/syntax",id:"extras-refinement/syntax",isDocsHomePage:!1,title:"Refinement Syntax",description:"Why refinement syntax?",source:"@site/../generated-docs/target/mdoc/extras-refinement/syntax.md",sourceDirName:"extras-refinement",slug:"/extras-refinement/syntax",permalink:"/docs/extras-refinement/syntax",tags:[],version:"current",sidebarPosition:2,frontMatter:{sidebar_position:2,id:"syntax",title:"Refinement Syntax"},sidebar:"tutorialSidebar",previous:{title:"Getting Started",permalink:"/docs/extras-refinement/getting-started"}},d=[{value:"Why <code>refinement</code> syntax?",id:"why-refinement-syntax",children:[],level:2},{value:"<code>refinement</code> syntax",id:"refinement-syntax",children:[{value:"Example: Valid Case",id:"example-valid-case",children:[],level:3},{value:"Example: Invalid Case",id:"example-invalid-case",children:[{value:"Only of them is invalid",id:"only-of-them-is-invalid",children:[],level:4},{value:"The other one is invalid",id:"the-other-one-is-invalid",children:[],level:4},{value:"More than one invalid",id:"more-than-one-invalid",children:[],level:4}],level:3}],level:2}],m={toc:d};function c(e){var n=e.components,t=(0,i.Z)(e,l);return(0,r.kt)("wrapper",(0,a.Z)({},m,t,{components:n,mdxType:"MDXLayout"}),(0,r.kt)("h2",{id:"why-refinement-syntax"},"Why ",(0,r.kt)("inlineCode",{parentName:"h2"},"refinement")," syntax?"),(0,r.kt)("p",null,"When you use ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/estatico/scala-newtype"},"newtype")," and ",(0,r.kt)("a",{parentName:"p",href:"https://github.com/fthomas/refined"},"refined")," together\nto have better type-safety, you often have some boilerplate code for runtime value validation\nwhen creating newtype + refinement type just like this."),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'YourRefinementType.from(value)\n  .toEither\n  .map(YourNewtype(_))\n  .leftMap(err => s"Failed to create YourNewtype: $err")\n')),(0,r.kt)("p",null,"In practice, it may look like"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport io.estatico.newtype.macros.newtype\nimport eu.timepit.refined.types.string.NonEmptyString\n\n@newtype case class Name(value: NonEmptyString)\n\nval validNameValue = "Kevin"\n// validNameValue: String = "Kevin"\nNonEmptyString.from(validNameValue)\n  .toEitherNel\n  .map(Name(_))\n  .leftMap(err => s"Failed to create Name: $err")\n// res1: Either[String, Name] = Right(value = Kevin)\n\nval invalidNameValue = ""\n// invalidNameValue: String = ""\nNonEmptyString.from(invalidNameValue)\n  .toEitherNel\n  .map(Name(_))\n  .leftMap(err => s"Failed to create Name: $err")\n// res2: Either[String, Name] = Left(\n//   value = "Failed to create Name: NonEmptyList(Predicate isEmpty() did not fail.)"\n// )\n')),(0,r.kt)("p",null,"or this"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport eu.timepit.refined.api._\nimport eu.timepit.refined.numeric._\nimport eu.timepit.refined.types.string.NonEmptyString\nimport io.estatico.newtype.macros.newtype\nimport io.estatico.newtype.ops._\n\nobject Types {\n  type PositiveInt = Int Refined Positive\n  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]\n  @newtype case class Id(value: PositiveInt)\n  \n  @newtype case class Name(value: NonEmptyString)\n  \n  final case class Person(id: Id, name: Name)\n}\nimport Types._\n\nval idValue = 999\n// idValue: Int = 999\n\nval id  = PositiveInt.from(idValue)\n            .toEitherNel\n            .map(Id(_))\n            .leftMap(err => s"Failed to create Types.Id: $err")\n// id: Either[String, Id] = Right(value = 999)\nprintln(id)\n// Right(999)\n\nval nameValue = "Kevin"\n// nameValue: String = "Kevin"\n\nval name  = NonEmptyString.from(nameValue)\n            .toEitherNel\n            .map(Name(_))\n            .leftMap(err => s"Failed to create Types.Name: $err")\n// name: Either[String, Name] = Right(value = Kevin)\nprintln(name)\n// Right(Kevin)\n\nval person = (id, name).parMapN(Person.apply)\n// person: Either[String, Person] = Right(\n//   value = Person(id = 999, name = Kevin)\n// )\nprintln(person)\n// Right(Person(999,Kevin))\n')),(0,r.kt)("h2",{id:"refinement-syntax"},(0,r.kt)("inlineCode",{parentName:"h2"},"refinement")," syntax"),(0,r.kt)("p",null,"The boilerplate code issue in newtype + refinement type creation can be fixed with ",(0,r.kt)("inlineCode",{parentName:"p"},"extras")," ",(0,r.kt)("inlineCode",{parentName:"p"},"refinement")," syntax so the following code snippet"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'YourRefinementType.from(value)\n  .toEither\n  .map(YourNewtype(_))\n  .leftMap(err => s"Failed to create YourNewtype: $err")\n')),(0,r.kt)("p",null,"becomes just"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"validateAs[YourNewtype](value)\n")),(0,r.kt)("p",null,"or"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},"value.validateAs[YourNewtype]\n")),(0,r.kt)("div",{className:"admonition admonition-note alert alert--secondary"},(0,r.kt)("div",{parentName:"div",className:"admonition-heading"},(0,r.kt)("h5",{parentName:"div"},(0,r.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,r.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,r.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M6.3 5.69a.942.942 0 0 1-.28-.7c0-.28.09-.52.28-.7.19-.18.42-.28.7-.28.28 0 .52.09.7.28.18.19.28.42.28.7 0 .28-.09.52-.28.7a1 1 0 0 1-.7.3c-.28 0-.52-.11-.7-.3zM8 7.99c-.02-.25-.11-.48-.31-.69-.2-.19-.42-.3-.69-.31H6c-.27.02-.48.13-.69.31-.2.2-.3.44-.31.69h1v3c.02.27.11.5.31.69.2.2.42.31.69.31h1c.27 0 .48-.11.69-.31.2-.19.3-.42.31-.69H8V7.98v.01zM7 2.3c-3.14 0-5.7 2.54-5.7 5.68 0 3.14 2.56 5.7 5.7 5.7s5.7-2.55 5.7-5.7c0-3.15-2.56-5.69-5.7-5.69v.01zM7 .98c3.86 0 7 3.14 7 7s-3.14 7-7 7-7-3.12-7-7 3.14-7 7-7z"}))),"note")),(0,r.kt)("div",{parentName:"div",className:"admonition-content"},(0,r.kt)("p",{parentName:"div"},"The idea of ",(0,r.kt)("inlineCode",{parentName:"p"},"validateAs[A](value)")," and ",(0,r.kt)("inlineCode",{parentName:"p"},"value.validateAs[A]")," is from ",(0,r.kt)("a",{parentName:"p",href:"https://leanpub.com/pfp-scala"},"Practical FP in Scala"),".\nThe syntax is not exactly the same, but the most important core logic of using ",(0,r.kt)("inlineCode",{parentName:"p"},"Coercible")," is the same."))),(0,r.kt)("h3",{id:"example-valid-case"},"Example: Valid Case"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport eu.timepit.refined.api._\nimport eu.timepit.refined.numeric._\nimport eu.timepit.refined.types.string.NonEmptyString\nimport io.estatico.newtype.macros.newtype\nimport extras.refinement.syntax.refinement._\n\nobject Types {\n  type PositiveInt = Int Refined Positive\n  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]\n  @newtype case class Id(value: PositiveInt)\n  \n  @newtype case class Name(value: NonEmptyString)\n  \n  final case class Person(id: Id, name: Name)\n}\nimport Types._\n\nval idValue = 999\n// idValue: Int = 999\n\nval id  = validateAs[Id](idValue)\n// id: cats.data.package.EitherNel[String, Id] = Right(value = 999)\nval id2 = idValue.validateAs[Id]\n// id2: cats.data.package.EitherNel[String, Id] = Right(value = 999)\nprintln(id)\n// Right(999)\nprintln(id2)\n// Right(999)\n\nval nameValue = "Kevin"\n// nameValue: String = "Kevin"\n\nval name  = validateAs[Name](nameValue)\n// name: cats.data.package.EitherNel[String, Name] = Right(value = Kevin)\nval name2 = nameValue.validateAs[Name]\n// name2: cats.data.package.EitherNel[String, Name] = Right(value = Kevin)\nprintln(name)\n// Right(Kevin)\nprintln(name2)\n// Right(Kevin)\n\nval person = (id, name).parMapN(Person.apply)\n// person: cats.data.package.EitherNel[String, Person] = Right(\n//   value = Person(id = 999, name = Kevin)\n// )\nprintln(person)\n// Right(Person(999,Kevin))\n')),(0,r.kt)("h3",{id:"example-invalid-case"},"Example: Invalid Case"),(0,r.kt)("h4",{id:"only-of-them-is-invalid"},"Only of them is invalid"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport eu.timepit.refined.api._\nimport eu.timepit.refined.numeric._\nimport eu.timepit.refined.types.string.NonEmptyString\nimport io.estatico.newtype.macros.newtype\nimport extras.refinement.syntax.refinement._\n\nobject Types {\n  type PositiveInt = Int Refined Positive\n  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]\n  @newtype case class Id(value: PositiveInt)\n  \n  @newtype case class Name(value: NonEmptyString)\n  \n  final case class Person(id: Id, name: Name)\n}\nimport Types._\n\nval idValue = 0\n// idValue: Int = 0\n\nval id  = validateAs[Id](idValue)\n// id: cats.data.package.EitherNel[String, Id] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Id: Predicate failed: (0 > 0).",\n//     tail = List()\n//   )\n// )\nval id2 = idValue.validateAs[Id]\n// id2: cats.data.package.EitherNel[String, Id] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Id: Predicate failed: (0 > 0).",\n//     tail = List()\n//   )\n// )\nprintln(id)\n// Left(NonEmptyList(Failed to create Types.Id: Predicate failed: (0 > 0).))\nprintln(id2)\n// Left(NonEmptyList(Failed to create Types.Id: Predicate failed: (0 > 0).))\n\nval nameValue = "Kevin"\n// nameValue: String = "Kevin"\n\nval name  = validateAs[Name](nameValue)\n// name: cats.data.package.EitherNel[String, Name] = Right(value = Kevin)\nval name2 = nameValue.validateAs[Name]\n// name2: cats.data.package.EitherNel[String, Name] = Right(value = Kevin)\nprintln(name)\n// Right(Kevin)\nprintln(name2)\n// Right(Kevin)\n\nval person = (id, name).parMapN(Person.apply)\n// person: cats.data.package.EitherNel[String, Person] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Id: Predicate failed: (0 > 0).",\n//     tail = List()\n//   )\n// )\nprintln(person)\n// Left(NonEmptyList(Failed to create Types.Id: Predicate failed: (0 > 0).))\n')),(0,r.kt)("h4",{id:"the-other-one-is-invalid"},"The other one is invalid"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport eu.timepit.refined.api._\nimport eu.timepit.refined.numeric._\nimport eu.timepit.refined.types.string.NonEmptyString\nimport io.estatico.newtype.macros.newtype\nimport extras.refinement.syntax.refinement._\n\nobject Types {\n  type PositiveInt = Int Refined Positive\n  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]\n  @newtype case class Id(value: PositiveInt)\n  \n  @newtype case class Name(value: NonEmptyString)\n  \n  final case class Person(id: Id, name: Name)\n}\nimport Types._\n\nval idValue = 999\n// idValue: Int = 999\n\nval id  = validateAs[Id](idValue)\n// id: cats.data.package.EitherNel[String, Id] = Right(value = 999)\nval id2 = idValue.validateAs[Id]\n// id2: cats.data.package.EitherNel[String, Id] = Right(value = 999)\nprintln(id)\n// Right(999)\nprintln(id2)\n// Right(999)\n\nval nameValue = ""\n// nameValue: String = ""\n\nval name  = validateAs[Name](nameValue)\n// name: cats.data.package.EitherNel[String, Name] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Name: Predicate isEmpty() did not fail.",\n//     tail = List()\n//   )\n// )\nval name2 = nameValue.validateAs[Name]\n// name2: cats.data.package.EitherNel[String, Name] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Name: Predicate isEmpty() did not fail.",\n//     tail = List()\n//   )\n// )\nprintln(name)\n// Left(NonEmptyList(Failed to create Types.Name: Predicate isEmpty() did not fail.))\nprintln(name2)\n// Left(NonEmptyList(Failed to create Types.Name: Predicate isEmpty() did not fail.))\n\nval person = (id, name).parMapN(Person.apply)\n// person: cats.data.package.EitherNel[String, Person] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Name: Predicate isEmpty() did not fail.",\n//     tail = List()\n//   )\n// )\nprintln(person)\n// Left(NonEmptyList(Failed to create Types.Name: Predicate isEmpty() did not fail.))\n')),(0,r.kt)("h4",{id:"more-than-one-invalid"},"More than one invalid"),(0,r.kt)("pre",null,(0,r.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport eu.timepit.refined.api._\nimport eu.timepit.refined.numeric._\nimport eu.timepit.refined.types.string.NonEmptyString\nimport io.estatico.newtype.macros.newtype\nimport extras.refinement.syntax.refinement._\n\nobject Types {\n  type PositiveInt = Int Refined Positive\n  object PositiveInt extends RefinedTypeOps[PositiveInt, Int]\n  @newtype case class Id(value: PositiveInt)\n  \n  @newtype case class Name(value: NonEmptyString)\n  \n  final case class Person(id: Id, name: Name)\n}\nimport Types._\n\nval idValue = 0\n// idValue: Int = 0\n\nval id  = validateAs[Id](idValue)\n// id: cats.data.package.EitherNel[String, Id] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Id: Predicate failed: (0 > 0).",\n//     tail = List()\n//   )\n// )\nval id2 = idValue.validateAs[Id]\n// id2: cats.data.package.EitherNel[String, Id] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Id: Predicate failed: (0 > 0).",\n//     tail = List()\n//   )\n// )\nprintln(id)\n// Left(NonEmptyList(Failed to create Types.Id: Predicate failed: (0 > 0).))\nprintln(id2)\n// Left(NonEmptyList(Failed to create Types.Id: Predicate failed: (0 > 0).))\n\nval nameValue = ""\n// nameValue: String = ""\n\nval name  = validateAs[Name](nameValue)\n// name: cats.data.package.EitherNel[String, Name] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Name: Predicate isEmpty() did not fail.",\n//     tail = List()\n//   )\n// )\nval name2 = nameValue.validateAs[Name]\n// name2: cats.data.package.EitherNel[String, Name] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Name: Predicate isEmpty() did not fail.",\n//     tail = List()\n//   )\n// )\nprintln(name)\n// Left(NonEmptyList(Failed to create Types.Name: Predicate isEmpty() did not fail.))\nprintln(name2)\n// Left(NonEmptyList(Failed to create Types.Name: Predicate isEmpty() did not fail.))\n\nval person = (id, name).parMapN(Person.apply)\n// person: cats.data.package.EitherNel[String, Person] = Left(\n//   value = NonEmptyList(\n//     head = "Failed to create Types.Id: Predicate failed: (0 > 0).",\n//     tail = List(\n//       "Failed to create Types.Name: Predicate isEmpty() did not fail."\n//     )\n//   )\n// )\nprintln(person)\n// Left(NonEmptyList(Failed to create Types.Id: Predicate failed: (0 > 0)., Failed to create Types.Name: Predicate isEmpty() did not fail.))\n')))}c.isMDXComponent=!0}}]);