"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[5455],{3905:(e,t,n)=>{n.d(t,{Zo:()=>p,kt:()=>m});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function o(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?o(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):o(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},o=Object.keys(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);for(r=0;r<o.length;r++)n=o[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var c=r.createContext({}),l=function(e){var t=r.useContext(c),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},p=function(e){var t=l(e.components);return r.createElement(c.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},u=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,o=e.originalType,c=e.parentName,p=s(e,["components","mdxType","originalType","parentName"]),u=l(n),m=a,h=u["".concat(c,".").concat(m)]||u[m]||d[m]||o;return n?r.createElement(h,i(i({ref:t},p),{},{components:n})):r.createElement(h,i({ref:t},p))}));function m(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var o=n.length,i=new Array(o);i[0]=u;var s={};for(var c in t)hasOwnProperty.call(t,c)&&(s[c]=t[c]);s.originalType=e,s.mdxType="string"==typeof e?e:a,i[1]=s;for(var l=2;l<o;l++)i[l]=n[l];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}u.displayName="MDXCreateElement"},5694:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>c,contentTitle:()=>i,default:()=>d,frontMatter:()=>o,metadata:()=>s,toc:()=>l});var r=n(7462),a=(n(7294),n(3905));const o={sidebar_position:3,id:"with-manual-test-context-creation",title:"With Manual TestContext Creation",sidebar_label:"With TestContext"},i=void 0,s={unversionedId:"extras-hedgehog/extras-hedgehog-ce3/with-syntax/with-manual-test-context-creation",id:"extras-hedgehog/extras-hedgehog-ce3/with-syntax/with-manual-test-context-creation",title:"With Manual TestContext Creation",description:"TestContext",source:"@site/../generated-docs/docs/extras-hedgehog/extras-hedgehog-ce3/with-syntax/with-manual-test-context-creation.md",sourceDirName:"extras-hedgehog/extras-hedgehog-ce3/with-syntax",slug:"/extras-hedgehog/extras-hedgehog-ce3/with-syntax/with-manual-test-context-creation",permalink:"/docs/extras-hedgehog/extras-hedgehog-ce3/with-syntax/with-manual-test-context-creation",draft:!1,tags:[],version:"current",sidebarPosition:3,frontMatter:{sidebar_position:3,id:"with-manual-test-context-creation",title:"With Manual TestContext Creation",sidebar_label:"With TestContext"},sidebar:"tutorialSidebar",previous:{title:"runIO",permalink:"/docs/extras-hedgehog/extras-hedgehog-ce3/with-syntax/run-io"},next:{title:"Getting Started",permalink:"/docs/extras-doobie-tools/"}},c={},l=[{value:"<code>TestContext</code>",id:"testcontext",level:2},{value:"completeThen",id:"completethen",level:2},{value:"errorThen",id:"errorthen",level:2}],p={toc:l};function d(e){let{components:t,...n}=e;return(0,a.kt)("wrapper",(0,r.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"testcontext"},(0,a.kt)("inlineCode",{parentName:"h2"},"TestContext")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"cats.effect.kernel.testkit.TestContext")," is from ",(0,a.kt)("inlineCode",{parentName:"p"},"cats-effect-kernel-testkit")," and it is"),(0,a.kt)("blockquote",null,(0,a.kt)("p",{parentName:"blockquote"},"A ",(0,a.kt)("inlineCode",{parentName:"p"},"scala.concurrent.ExecutionContext")," implementation that can simulate async boundaries and time passage")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"extras-hedgehog-ce3")," uses it and if you need to manually create it you can do so if you do not use ",(0,a.kt)("inlineCode",{parentName:"p"},"withIO")," or ",(0,a.kt)("inlineCode",{parentName:"p"},"runIO"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"implicit val ticker: Ticker = Ticker.withNewTestContext()\n")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"Ticker")," is a value class for ",(0,a.kt)("inlineCode",{parentName:"p"},"TestContext")," so you can also do"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"import cats.effect.kernel.testkit.TestContext\nimport extras.hedgehog.ce3.Ticker // You don't need to import it if you're using extras.hedgehog.ce3.syntax.runner\n\nimplicit val ticker: Ticker = Ticker(TestContext())\n")),(0,a.kt)("h2",{id:"completethen"},"completeThen"),(0,a.kt)("p",null,"Use ",(0,a.kt)("inlineCode",{parentName:"p"},"CatsEffectRunner")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"completeThen")," instead of ",(0,a.kt)("inlineCode",{parentName:"p"},"unsafeRunSync()"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala",metastring:"{17} mdoc:reset-object","{17}":!0,"mdoc:reset-object":!0},'import cats.effect._\n\nimport extras.hedgehog.ce3.syntax.runner._\n\nimport hedgehog._\nimport hedgehog.runner._\n\nobject SomeSpec extends Properties {\n  \n  override def tests: List[Test] = List(\n    property("test and completeThen", testCatsEffectRunnerWithCompleteThen)\n  )\n  \n  def testCatsEffectRunnerWithCompleteThen: Property = for {\n    n <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n")\n  } yield {\n    implicit val ticker: Ticker = Ticker.withNewTestContext()\n\n    val expected = n\n    val actual = IO(n)\n\n    actual.completeThen { actual =>\n      actual ==== expected\n    }\n  }\n}\n')),(0,a.kt)("h2",{id:"errorthen"},"errorThen"),(0,a.kt)("p",null,"If you want to test with ",(0,a.kt)("inlineCode",{parentName:"p"},"IO")," which may result in some ",(0,a.kt)("inlineCode",{parentName:"p"},"Exception")," thrown, you can use ",(0,a.kt)("inlineCode",{parentName:"p"},"errorThen")," instead of ",(0,a.kt)("inlineCode",{parentName:"p"},"unsafeRunSync()")," and ",(0,a.kt)("inlineCode",{parentName:"p"},"Try"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala",metastring:"{23} mdoc:reset-object","{23}":!0,"mdoc:reset-object":!0},'import cats.effect._\n\nimport extras.hedgehog.ce3.syntax.runner._\n\nimport hedgehog._\nimport hedgehog.runner._\n\nobject SomeSpec extends Properties {\n\n  override def tests: List[Test] = List(\n    property("test and errorThen", testCatsEffectRunnerWithErrorThen)\n  )\n\n  def testCatsEffectRunnerWithErrorThen: Property = for {\n    message <- Gen.string(Gen.alphaNum, Range.linear(1, 10)).log("message")\n    error <- Gen\n      .element1(\n        TestError.someTestError(s"Don\'t worry it\'s only a test error. $message"),\n        TestError.anotherTestError(s"Don\'t worry it\'s only a test error. $message")\n      )\n      .log("error")\n  } yield {\n    implicit val ticker: Ticker = Ticker.withNewTestContext()\n\n    val expected = error\n    val actual = IO.raiseError[Int](error)\n\n    actual.errorThen { actual =>\n      actual ==== expected\n    }\n  }\n\n  sealed abstract class TestError(val message: String) extends RuntimeException(message)\n\n  object TestError {\n    final case class SomeTestError(override val message: String) extends TestError(message)\n    final case class AnotherTestError(override val message: String) extends TestError(message)\n\n    def anotherTestError(message: String): TestError = AnotherTestError(message)\n    def someTestError(message: String): TestError = SomeTestError(message)\n\n  }\n}\n')))}d.isMDXComponent=!0}}]);