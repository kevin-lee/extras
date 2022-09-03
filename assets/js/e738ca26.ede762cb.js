"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[378],{3905:(a,n,e)=>{e.d(n,{Zo:()=>d,kt:()=>m});var t=e(7294);function c(a,n,e){return n in a?Object.defineProperty(a,n,{value:e,enumerable:!0,configurable:!0,writable:!0}):a[n]=e,a}function i(a,n){var e=Object.keys(a);if(Object.getOwnPropertySymbols){var t=Object.getOwnPropertySymbols(a);n&&(t=t.filter((function(n){return Object.getOwnPropertyDescriptor(a,n).enumerable}))),e.push.apply(e,t)}return e}function r(a){for(var n=1;n<arguments.length;n++){var e=null!=arguments[n]?arguments[n]:{};n%2?i(Object(e),!0).forEach((function(n){c(a,n,e[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(a,Object.getOwnPropertyDescriptors(e)):i(Object(e)).forEach((function(n){Object.defineProperty(a,n,Object.getOwnPropertyDescriptor(e,n))}))}return a}function l(a,n){if(null==a)return{};var e,t,c=function(a,n){if(null==a)return{};var e,t,c={},i=Object.keys(a);for(t=0;t<i.length;t++)e=i[t],n.indexOf(e)>=0||(c[e]=a[e]);return c}(a,n);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(a);for(t=0;t<i.length;t++)e=i[t],n.indexOf(e)>=0||Object.prototype.propertyIsEnumerable.call(a,e)&&(c[e]=a[e])}return c}var s=t.createContext({}),o=function(a){var n=t.useContext(s),e=n;return a&&(e="function"==typeof a?a(n):r(r({},n),a)),e},d=function(a){var n=o(a.components);return t.createElement(s.Provider,{value:n},a.children)},u={inlineCode:"code",wrapper:function(a){var n=a.children;return t.createElement(t.Fragment,{},n)}},$=t.forwardRef((function(a,n){var e=a.components,c=a.mdxType,i=a.originalType,s=a.parentName,d=l(a,["components","mdxType","originalType","parentName"]),$=o(e),m=c,p=$["".concat(s,".").concat(m)]||$[m]||u[m]||i;return e?t.createElement(p,r(r({ref:n},d),{},{components:e})):t.createElement(p,r({ref:n},d))}));function m(a,n){var e=arguments,c=n&&n.mdxType;if("string"==typeof a||c){var i=e.length,r=new Array(i);r[0]=$;var l={};for(var s in n)hasOwnProperty.call(n,s)&&(l[s]=n[s]);l.originalType=a,l.mdxType="string"==typeof a?a:c,r[1]=l;for(var o=2;o<i;o++)r[o]=e[o];return t.createElement.apply(null,r)}return t.createElement.apply(null,e)}$.displayName="MDXCreateElement"},9987:(a,n,e)=>{e.r(n),e.d(n,{assets:()=>s,contentTitle:()=>r,default:()=>u,frontMatter:()=>i,metadata:()=>l,toc:()=>o});var t=e(7462),c=(e(7294),e(3905));const i={sidebar_position:2,id:"eithert",title:"EitherT"},r=void 0,l={unversionedId:"extras-cats/eithert",id:"extras-cats/eithert",title:"EitherT",description:"Extension Methods for EitherT",source:"@site/../generated-docs/target/mdoc/extras-cats/eithert.md",sourceDirName:"extras-cats",slug:"/extras-cats/eithert",permalink:"/docs/extras-cats/eithert",draft:!1,tags:[],version:"current",sidebarPosition:2,frontMatter:{sidebar_position:2,id:"eithert",title:"EitherT"},sidebar:"tutorialSidebar",previous:{title:"extras-cats: Getting Started",permalink:"/docs/extras-cats/"},next:{title:"OptionT",permalink:"/docs/extras-cats/optiont"}},s={},o=[{value:"Extension Methods for EitherT",id:"extension-methods-for-eithert",level:2},{value:"<code>eitherT</code> / <code>t</code> for <code>F[Either[A, B]]</code>",id:"eithert--t-for-feithera-b",level:3},{value:"<code>eitherT</code> / <code>t</code> for <code>Either[A, B]</code>",id:"eithert--t-for-eithera-b",level:3},{value:"<code>rightT</code> for <code>F[B]</code>",id:"rightt-for-fb",level:3},{value:"<code>leftT</code> for <code>F[A]</code>",id:"leftt-for-fa",level:3},{value:"Example",id:"example",level:2}],d={toc:o};function u(a){let{components:n,...e}=a;return(0,c.kt)("wrapper",(0,t.Z)({},d,e,{components:n,mdxType:"MDXLayout"}),(0,c.kt)("h2",{id:"extension-methods-for-eithert"},"Extension Methods for EitherT"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"import extras.cats.syntax.either._\n")),(0,c.kt)("p",null,"or"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"import extras.cats.syntax.all._\n")),(0,c.kt)("h3",{id:"eithert--t-for-feithera-b"},(0,c.kt)("inlineCode",{parentName:"h3"},"eitherT")," / ",(0,c.kt)("inlineCode",{parentName:"h3"},"t")," for ",(0,c.kt)("inlineCode",{parentName:"h3"},"F[Either[A, B]]")),(0,c.kt)("p",null,"When you have ",(0,c.kt)("inlineCode",{parentName:"p"},"fab: F[Either[A, B]]"),", instead of ",(0,c.kt)("inlineCode",{parentName:"p"},"EitherT(fab)"),", you can simply do"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"fab.eitherT // EitherT[F, A, B]\n// or\nfab.t // EitherT[F, A, B]\n")),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport cats.effect._\n\nimport extras.cats.syntax.all._\n\nval fab = IO.pure(1.asRight[String])\n// fab: IO[Either[String, Int]] = Pure(a = Right(value = 1))\nfab.t\n// res1: cats.data.EitherT[IO, String, Int] = EitherT(\n//   value = Pure(a = Right(value = 1))\n// )\n\nval f = IO(println("Hello").asRight[String])\n// f: IO[Either[String, Unit]] = Delay(\n//   thunk = <function0>,\n//   trace = StackTrace(\n//     stackTrace = List(\n//       cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//       cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//       cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//       cats.effect.IO$.delay(IO.scala:1176),\n//       cats.effect.IO$.apply(IO.scala:1144),\n//       repl.MdocSession$App0$.<clinit>(eithert.md:26),\n//       repl.MdocSession$App.<init>(eithert.md:5),\n//       repl.MdocSession$.app(eithert.md:3),\n//       mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//       scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//       scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//       scala.Console$.withErr(Console.scala:193),\n//       mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//       scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//       scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//       scala.Console$.withOut(Console.scala:164),\n//       mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//       mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//       mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//     )\n//   )\n// )\nf.t\n// res2: cats.data.EitherT[IO, String, Unit] = EitherT(\n//   value = Delay(\n//     thunk = <function0>,\n//     trace = StackTrace(\n//       stackTrace = List(\n//         cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//         cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//         cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//         cats.effect.IO$.delay(IO.scala:1176),\n//         cats.effect.IO$.apply(IO.scala:1144),\n//         repl.MdocSession$App0$.<clinit>(eithert.md:26),\n//         repl.MdocSession$App.<init>(eithert.md:5),\n//         repl.MdocSession$.app(eithert.md:3),\n//         mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//         scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//         scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//         scala.Console$.withErr(Console.scala:193),\n//         mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//         scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//         scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//         scala.Console$.withOut(Console.scala:164),\n//         mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//         mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//         mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//       )\n//     )\n//   )\n// )\n')),(0,c.kt)("h3",{id:"eithert--t-for-eithera-b"},(0,c.kt)("inlineCode",{parentName:"h3"},"eitherT")," / ",(0,c.kt)("inlineCode",{parentName:"h3"},"t")," for ",(0,c.kt)("inlineCode",{parentName:"h3"},"Either[A, B]")),(0,c.kt)("p",null,"When you have ",(0,c.kt)("inlineCode",{parentName:"p"},"ab: Either[A, B]"),", instead of ",(0,c.kt)("inlineCode",{parentName:"p"},"EitherT.fromEither[F](ab)"),", you can simply do"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"ab.eitherT[F] // EitherT[F, A, B]\n// or\nab.t[F] // EitherT[F, A, B]\n")),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"import cats.syntax.all._\nimport cats.effect._\n\nimport extras.cats.syntax.all._\n\nval ab = 1.asRight[String]\n// ab: Either[String, Int] = Right(value = 1)\nab.t[IO]\n// res4: cats.data.EitherT[IO, String, Int] = EitherT(\n//   value = Pure(a = Right(value = 1))\n// )\n")),(0,c.kt)("h3",{id:"rightt-for-fb"},(0,c.kt)("inlineCode",{parentName:"h3"},"rightT")," for ",(0,c.kt)("inlineCode",{parentName:"h3"},"F[B]")),(0,c.kt)("p",null,"When you have ",(0,c.kt)("inlineCode",{parentName:"p"},"fb: F[B]"),", instead of ",(0,c.kt)("inlineCode",{parentName:"p"},"EitherT.right[A](fb)"),", you can simply do"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"fb.rightT[A] // EitherT[F, A, B]\n")),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.effect._\n\nimport extras.cats.syntax.all._\n\nval fb = IO.pure(1)\n// fb: IO[Int] = Pure(a = 1)\nfb.rightT[String]\n// res6: cats.data.EitherT[IO, String, Int] = EitherT(\n//   value = Map(\n//     source = Pure(a = 1),\n//     f = cats.data.EitherT$RightPartiallyApplied$$$Lambda$8145/0x000000010230b840@462bf07c,\n//     trace = StackTrace(\n//       stackTrace = List(\n//         cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//         cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//         cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//         cats.effect.IO.map(IO.scala:106),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:872),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:865),\n//         cats.data.EitherT$RightPartiallyApplied$.apply$extension(EitherT.scala:694),\n//         extras.cats.syntax.EitherSyntax$EitherTFAOps$.rightT$extension(EitherSyntax.scala:37),\n//         repl.MdocSession$App5$.<clinit>(eithert.md:68),\n//         repl.MdocSession$App3$.<clinit>(eithert.md:53),\n//         repl.MdocSession$App0$.<clinit>(eithert.md:32),\n//         repl.MdocSession$App.<init>(eithert.md:5),\n//         repl.MdocSession$.app(eithert.md:3),\n//         mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//         scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//         scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//         scala.Console$.withErr(Console.scala:193),\n//         mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//         scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//         scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//         scala.Console$.withOut(Console.scala:164),\n//         mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//         mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//         mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//       )\n//     )\n//   )\n// )\n\nval f = IO(println("Hello"))\n// f: IO[Unit] = Delay(\n//   thunk = <function0>,\n//   trace = StackTrace(\n//     stackTrace = List(\n//       cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//       cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//       cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//       cats.effect.IO$.delay(IO.scala:1176),\n//       cats.effect.IO$.apply(IO.scala:1144),\n//       repl.MdocSession$App5$.<clinit>(eithert.md:71),\n//       repl.MdocSession$App3$.<clinit>(eithert.md:53),\n//       repl.MdocSession$App0$.<clinit>(eithert.md:32),\n//       repl.MdocSession$App.<init>(eithert.md:5),\n//       repl.MdocSession$.app(eithert.md:3),\n//       mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//       scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//       scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//       scala.Console$.withErr(Console.scala:193),\n//       mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//       scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//       scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//       scala.Console$.withOut(Console.scala:164),\n//       mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//       mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//       mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//     )\n//   )\n// )\nf.rightT[String]\n// res7: cats.data.EitherT[IO, String, Unit] = EitherT(\n//   value = Map(\n//     source = Delay(\n//       thunk = <function0>,\n//       trace = StackTrace(\n//         stackTrace = List(\n//           cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//           cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//           cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//           cats.effect.IO$.delay(IO.scala:1176),\n//           cats.effect.IO$.apply(IO.scala:1144),\n//           repl.MdocSession$App5$.<clinit>(eithert.md:71),\n//           repl.MdocSession$App3$.<clinit>(eithert.md:53),\n//           repl.MdocSession$App0$.<clinit>(eithert.md:32),\n//           repl.MdocSession$App.<init>(eithert.md:5),\n//           repl.MdocSession$.app(eithert.md:3),\n//           mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//           scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//           scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//           scala.Console$.withErr(Console.scala:193),\n//           mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//           scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//           scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//           scala.Console$.withOut(Console.scala:164),\n//           mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//           mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//           mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//         )\n//       )\n//     ),\n//     f = cats.data.EitherT$RightPartiallyApplied$$$Lambda$8145/0x000000010230b840@462bf07c,\n//     trace = StackTrace(\n//       stackTrace = List(\n//         cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//         cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//         cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//         cats.effect.IO.map(IO.scala:106),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:872),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:865),\n//         cats.data.EitherT$RightPartiallyApplied$.apply$extension(EitherT.scala:694),\n// ...\n')),(0,c.kt)("h3",{id:"leftt-for-fa"},(0,c.kt)("inlineCode",{parentName:"h3"},"leftT")," for ",(0,c.kt)("inlineCode",{parentName:"h3"},"F[A]")),(0,c.kt)("p",null,"When you have ",(0,c.kt)("inlineCode",{parentName:"p"},"fa: F[A]"),", instead of ",(0,c.kt)("inlineCode",{parentName:"p"},"EitherT.left[B](fa)"),", you can simply do"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},"fa.leftT[B] // EitherT[F, A, B]\n")),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.effect._\n\nimport extras.cats.syntax.all._\n\nval fa = IO.pure("ERROR!!!")\n// fa: IO[String] = Pure(a = "ERROR!!!")\nfa.leftT[Int]\n// res9: cats.data.EitherT[IO, String, Int] = EitherT(\n//   value = Map(\n//     source = Pure(a = "ERROR!!!"),\n//     f = cats.data.EitherT$LeftPartiallyApplied$$$Lambda$8146/0x0000000102338840@208b406d,\n//     trace = StackTrace(\n//       stackTrace = List(\n//         cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//         cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//         cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//         cats.effect.IO.map(IO.scala:106),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:872),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:865),\n//         cats.data.EitherT$LeftPartiallyApplied$.apply$extension(EitherT.scala:658),\n//         extras.cats.syntax.EitherSyntax$EitherTFAOps$.leftT$extension(EitherSyntax.scala:38),\n//         repl.MdocSession$App8$.<clinit>(eithert.md:92),\n//         repl.MdocSession$App5$.<clinit>(eithert.md:77),\n//         repl.MdocSession$App3$.<clinit>(eithert.md:53),\n//         repl.MdocSession$App0$.<clinit>(eithert.md:32),\n//         repl.MdocSession$App.<init>(eithert.md:5),\n//         repl.MdocSession$.app(eithert.md:3),\n//         mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//         scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//         scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//         scala.Console$.withErr(Console.scala:193),\n//         mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//         scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//         scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//         scala.Console$.withOut(Console.scala:164),\n//         mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//         mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//         mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//       )\n//     )\n//   )\n// )\n\nval f = IO(println("ERROR!!!"))\n// f: IO[Unit] = Delay(\n//   thunk = <function0>,\n//   trace = StackTrace(\n//     stackTrace = List(\n//       cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//       cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//       cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//       cats.effect.IO$.delay(IO.scala:1176),\n//       cats.effect.IO$.apply(IO.scala:1144),\n//       repl.MdocSession$App8$.<clinit>(eithert.md:95),\n//       repl.MdocSession$App5$.<clinit>(eithert.md:77),\n//       repl.MdocSession$App3$.<clinit>(eithert.md:53),\n//       repl.MdocSession$App0$.<clinit>(eithert.md:32),\n//       repl.MdocSession$App.<init>(eithert.md:5),\n//       repl.MdocSession$.app(eithert.md:3),\n//       mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//       scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//       scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//       scala.Console$.withErr(Console.scala:193),\n//       mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//       scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//       scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//       scala.Console$.withOut(Console.scala:164),\n//       mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//       mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//       mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//     )\n//   )\n// )\nf.leftT[Int]\n// res10: cats.data.EitherT[IO, Unit, Int] = EitherT(\n//   value = Map(\n//     source = Delay(\n//       thunk = <function0>,\n//       trace = StackTrace(\n//         stackTrace = List(\n//           cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//           cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//           cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//           cats.effect.IO$.delay(IO.scala:1176),\n//           cats.effect.IO$.apply(IO.scala:1144),\n//           repl.MdocSession$App8$.<clinit>(eithert.md:95),\n//           repl.MdocSession$App5$.<clinit>(eithert.md:77),\n//           repl.MdocSession$App3$.<clinit>(eithert.md:53),\n//           repl.MdocSession$App0$.<clinit>(eithert.md:32),\n//           repl.MdocSession$App.<init>(eithert.md:5),\n//           repl.MdocSession$.app(eithert.md:3),\n//           mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89),\n//           scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//           scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//           scala.Console$.withErr(Console.scala:193),\n//           mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89),\n//           scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18),\n//           scala.util.DynamicVariable.withValue(DynamicVariable.scala:59),\n//           scala.Console$.withOut(Console.scala:164),\n//           mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88),\n//           mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47),\n//           mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n//         )\n//       )\n//     ),\n//     f = cats.data.EitherT$LeftPartiallyApplied$$$Lambda$8146/0x0000000102338840@208b406d,\n//     trace = StackTrace(\n//       stackTrace = List(\n//         cats.effect.internals.IOTracing$.buildFrame(IOTracing.scala:48),\n//         cats.effect.internals.IOTracing$.buildCachedFrame(IOTracing.scala:39),\n//         cats.effect.internals.IOTracing$.cached(IOTracing.scala:34),\n//         cats.effect.IO.map(IO.scala:106),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:872),\n//         cats.effect.IOLowPriorityInstances$IOEffect.map(IO.scala:865),\n//         cats.data.EitherT$LeftPartiallyApplied$.apply$extension(EitherT.scala:65...\n')),(0,c.kt)("h2",{id:"example"},"Example"),(0,c.kt)("pre",null,(0,c.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport cats.effect._\n\nimport extras.cats.syntax.all._\n\nfinal case class MyError(message: String)\n\ndef foo[F[_]: Sync](n: Int): F[Int] = Sync[F].pure(n * 2)\n\ndef bar[F[_]: Sync](n: Int): F[Either[MyError, Int]] =\n  if (n < 0)\n    Sync[F].pure(MyError(s"n cannot be a negative number. [n: $n]").asLeft)\n  else\n    Sync[F].pure((n + 100).asRight)\n\ndef divide[F[_]: Sync](a: Int, b: Int): F[Either[MyError, Int]] =\n  if (b == 0)\n    MyError(s"You can divide number by 0. [a: $a, b: $b]").asLeft.pure[F]\n  else\n    Sync[F].delay((a / b).asRight)\n\ndef run[F[_]: Sync](): F[Either[MyError, Int]] = (for {\n  a <- foo(123).rightT\n  b <- 2.rightTF[F, MyError]\n  c <- bar(b).eitherT\n  d <- divide(a, b).t\n} yield d).value\n\nprintln(run[IO]().unsafeRunSync())\n// Right(123)\n')))}u.isMDXComponent=!0}}]);