"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[9520],{3905:(e,t,n)=>{n.d(t,{Zo:()=>p,kt:()=>m});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var c=r.createContext({}),l=function(e){var t=r.useContext(c),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},p=function(e){var t=l(e.components);return r.createElement(c.Provider,{value:t},e.children)},u="mdxType",d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},h=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,c=e.parentName,p=s(e,["components","mdxType","originalType","parentName"]),u=l(n),h=a,m=u["".concat(c,".").concat(h)]||u[h]||d[h]||o;return n?r.createElement(m,i(i({ref:t},p),{},{components:n})):r.createElement(m,i({ref:t},p))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,i=new Array(o);i[0]=h;var s={};for(var c in t)hasOwnProperty.call(t,c)&&(s[c]=t[c]);s.originalType=e,s[u]="string"==typeof e?e:a,i[1]=s;for(var l=2;l<o;l++)i[l]=n[l];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}h.displayName="MDXCreateElement"},7628:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>c,contentTitle:()=>i,default:()=>d,frontMatter:()=>o,metadata:()=>s,toc:()=>l});var r=n(7462),a=(n(7294),n(3905));const o={sidebar_position:1,id:"with-io",title:"withIO"},i=void 0,s={unversionedId:"extras-hedgehog/extras-hedgehog-ce3/with-runner/with-io",id:"extras-hedgehog/extras-hedgehog-ce3/with-runner/with-io",title:"withIO",description:"withIO",source:"@site/../generated-docs/docs/extras-hedgehog/extras-hedgehog-ce3/with-runner/with-io.md",sourceDirName:"extras-hedgehog/extras-hedgehog-ce3/with-runner",slug:"/extras-hedgehog/extras-hedgehog-ce3/with-runner/with-io",permalink:"/docs/extras-hedgehog/extras-hedgehog-ce3/with-runner/with-io",draft:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,id:"with-io",title:"withIO"},sidebar:"tutorialSidebar",previous:{title:"With CatsEffectRunner",permalink:"/docs/category/with-catseffectrunner"},next:{title:"runIO",permalink:"/docs/extras-hedgehog/extras-hedgehog-ce3/with-runner/run-io"}},c={},l=[{value:"<code>withIO</code>",id:"withio",level:2},{value:"completeThen",id:"completethen",level:3},{value:"errorThen",id:"errorthen",level:3}],p={toc:l},u="wrapper";function d(e){let{components:t,...n}=e;return(0,a.kt)(u,(0,r.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"withio"},(0,a.kt)("inlineCode",{parentName:"h2"},"withIO")),(0,a.kt)("p",null,"If you just run ",(0,a.kt)("inlineCode",{parentName:"p"},"IO.unsafeRunSync()")," in a test, it may not end and just hang. extras for hedgehog and cats-effect 3 (",(0,a.kt)("inlineCode",{parentName:"p"},"extras-hedgehog-ce3"),") can solve it with ",(0,a.kt)("inlineCode",{parentName:"p"},"withIO"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"def withIO(test: => Ticker => Result): Result\n")),(0,a.kt)("p",null,"To use ",(0,a.kt)("inlineCode",{parentName:"p"},"withIO"),", your test needs to extend ",(0,a.kt)("inlineCode",{parentName:"p"},"CatsEffectRunner")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"import hedgehog.runner._\nimport extras.hedgehog.ce3.CatsEffectRunner\n\nobject SomeSpec extends Properties with CatsEffectRunner {\n")),(0,a.kt)("p",null,"then in your test, use ",(0,a.kt)("inlineCode",{parentName:"p"},"withIO")," like"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala",metastring:"{2}","{2}":!0},"def test: Result = \n  withIO { implicit ticker =>\n    // Your test\n    val exected = ??? // A\n    val actual = ??? // IO[A]\n    actual.completeThen(_ ==== expected)\n  }\n")),(0,a.kt)("p",null,"or"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala",metastring:"{4}","{4}":!0},'def test: Property =\n  for {\n    n <- Gen.int(Range.linear(1, 100)).log("n")\n  } yield withIO { implicit ticker =>\n    // Your test\n    val exected = ??? // A\n    val actual = ??? // IO[A]\n    actual.completeThen(_ ==== expected)\n  }\n')),(0,a.kt)("h3",{id:"completethen"},"completeThen"),(0,a.kt)("p",null,"Use ",(0,a.kt)("inlineCode",{parentName:"p"},"CatsEffectRunner")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"completeThen")," instead of ",(0,a.kt)("inlineCode",{parentName:"p"},"unsafeRunSync()"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala",metastring:"{8,16,21-23} mdoc:reset-object","{8,16,21-23}":!0,"mdoc:reset-object":!0},'import cats.effect._\n\nimport extras.hedgehog.ce3.CatsEffectRunner\n\nimport hedgehog._\nimport hedgehog.runner._\n\nobject SomeSpec extends Properties with CatsEffectRunner {\n  \n  override def tests: List[Test] = List(\n    property("test with CatsEffectRunner and completeThen", testCatsEffectRunnerWithCompleteThen)\n  )\n  \n  def testCatsEffectRunnerWithCompleteThen: Property = for {\n    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")\n  } yield withIO { implicit ticker =>\n\n    val expected = n\n    val actual = IO(n)\n\n    actual.completeThen { actual =>\n      actual ==== expected\n    }\n  }\n}\n\n')),(0,a.kt)("h3",{id:"errorthen"},"errorThen"),(0,a.kt)("p",null,"If you want to test with ",(0,a.kt)("inlineCode",{parentName:"p"},"IO")," which may result in some ",(0,a.kt)("inlineCode",{parentName:"p"},"Exception")," thrown, you can use ",(0,a.kt)("inlineCode",{parentName:"p"},"errorThen")," instead of ",(0,a.kt)("inlineCode",{parentName:"p"},"unsafeRunSync()")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"Try"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala",metastring:"{8,22,27-29} mdoc:reset-object","{8,22,27-29}":!0,"mdoc:reset-object":!0},'import cats.effect._\n\nimport extras.hedgehog.ce3.CatsEffectRunner\n\nimport hedgehog._\nimport hedgehog.runner._\n\nobject SomeSpec extends Properties with CatsEffectRunner {\n\n  override def tests: List[Test] = List(\n    property("test with CatsEffectRunner and errorThen", testCatsEffectRunnerWithErrorThen)\n  )\n\n  def testCatsEffectRunnerWithErrorThen: Property = for {\n    message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")\n    error <- Gen\n      .element1(\n        TestError.someTestError(s"Don\'t worry it\'s only a test error. $message"),\n        TestError.anotherTestError(s"Don\'t worry it\'s only a test error. $message")\n      )\n      .log("error")\n  } yield withIO { implicit ticker =>\n\n    val expected = error\n    val actual = IO.raiseError[Int](error)\n\n    actual.errorThen { actual =>\n      actual ==== expected\n    }\n  }\n\n  sealed abstract class TestError(val message: String) extends RuntimeException(message)\n\n  object TestError {\n    final case class SomeTestError(override val message: String) extends TestError(message)\n    final case class AnotherTestError(override val message: String) extends TestError(message)\n\n    def anotherTestError(message: String): TestError = AnotherTestError(message)\n    def someTestError(message: String): TestError = SomeTestError(message)\n\n  }\n}\n')))}d.isMDXComponent=!0}}]);