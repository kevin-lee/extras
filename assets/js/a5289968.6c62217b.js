"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[1669],{3905:(e,t,n)=>{n.d(t,{Zo:()=>p,kt:()=>d});var o=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,o)}return n}function r(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function l(e,t){if(null==e)return{};var n,o,a=function(e,t){if(null==e)return{};var n,o,a={},i=Object.keys(e);for(o=0;o<i.length;o++)n=i[o],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(o=0;o<i.length;o++)n=i[o],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var s=o.createContext({}),c=function(e){var t=o.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):r(r({},t),e)),n},p=function(e){var t=c(e.components);return o.createElement(s.Provider,{value:t},e.children)},u="mdxType",f={inlineCode:"code",wrapper:function(e){var t=e.children;return o.createElement(o.Fragment,{},t)}},m=o.forwardRef((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,s=e.parentName,p=l(e,["components","mdxType","originalType","parentName"]),u=c(n),m=a,d=u["".concat(s,".").concat(m)]||u[m]||f[m]||i;return n?o.createElement(d,r(r({ref:t},p),{},{components:n})):o.createElement(d,r({ref:t},p))}));function d(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,r=new Array(i);r[0]=m;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[u]="string"==typeof e?e:a,r[1]=l;for(var c=2;c<i;c++)r[c]=n[c];return o.createElement.apply(null,r)}return o.createElement.apply(null,n)}m.displayName="MDXCreateElement"},6061:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>r,default:()=>f,frontMatter:()=>i,metadata:()=>l,toc:()=>c});var o=n(7462),a=(n(7294),n(3905));const i={sidebar_position:4,sidebar_label:"Effectie",id:"effectie",title:"Extras - Testing Tools for Effectie"},r=void 0,l={unversionedId:"extras-testing-tools/effectie/effectie",id:"extras-testing-tools/effectie/effectie",title:"Extras - Testing Tools for Effectie",description:"StubToolsFx.stub",source:"@site/../generated-docs/docs/extras-testing-tools/effectie/effectie.md",sourceDirName:"extras-testing-tools/effectie",slug:"/extras-testing-tools/effectie/",permalink:"/docs/extras-testing-tools/effectie/",draft:!1,tags:[],version:"current",sidebarPosition:4,frontMatter:{sidebar_position:4,sidebar_label:"Effectie",id:"effectie",title:"Extras - Testing Tools for Effectie"},sidebar:"tutorialSidebar",previous:{title:"Cats",permalink:"/docs/extras-testing-tools/cats/"},next:{title:"Getting Started",permalink:"/docs/extras-concurrent/"}},s={},c=[{value:"<code>StubToolsFx.stub</code>",id:"stubtoolsfxstub",level:2},{value:"Example",id:"example",level:3}],p={toc:c},u="wrapper";function f(e){let{components:t,...n}=e;return(0,a.kt)(u,(0,o.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"stubtoolsfxstub"},(0,a.kt)("inlineCode",{parentName:"h2"},"StubToolsFx.stub")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"StubToolsFx.stub")," is a tool for a stub (a simple function for testing) so that you don't need to use mock frameworks."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"import cats.{Functor, Monad}\nimport cats.syntax.all._\n\nimport effectie.core.Fx\n\nimport extras.testing.StubToolsFx\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"def fooStub[F[_]: Fx](f: => Option[A]): FooStub[F] = new FooStub[F] {\n  def foo: F[A] = StubToolsFx.stub(f) // F[A]\n}\n// If f is None, it will raise MissingStubException with the line number pointing where it's missing\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"def fooStub[F[_]: Fx: Functor](f: => Option[() => A]): FooStub[F] = new FooStub[F] {\n  def foo(): F[A] = StubToolsFx.stub(f).map(_()) // F[A]\n}\n// If f is None, it will raise MissingStubException with the line number pointing where it's missing\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"def fooStub[F[_]: Fx: Functor](f: => Option[A => B]): FooStub[F] = new FooStub[F] {\n  def foo(a: A): F[B] = StubToolsFx.stub(f).map(_(a)) // F[B]\n}\n// If f is None, it will raise MissingStubException with the line number pointing where it's missing\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"def fooStub[F[_]: Fx: Monad](f: => Option[A => F[B]]): FooStub[F] = new FooStub[F] {\n  def foo(a: A): F[B] = StubToolsFx.stub(f).flatMap(_(a)) // F[B]\n}\n// If f is None, it will raise MissingStubException with the line number pointing where it's missing\n")),(0,a.kt)("h3",{id:"example"},"Example"),(0,a.kt)("p",null,"e.g.)"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.Monad\nimport cats.syntax.all._\nimport eu.timepit.refined.types.all._\nimport eu.timepit.refined.cats._\nimport eu.timepit.refined.auto._\nimport io.estatico.newtype.macros.newtype\nimport extras.testing.StubToolsFx\n\nimport effectie.core._\nimport effectie.syntax.all._\n\nobject types {\n  @newtype case class Id(value: PosInt)\n  @newtype case class Name(value: NonEmptyString)\n}\nimport types._\n\ntrait MyService[F[_]] {\n  def getName(id: Id): F[Option[Name]]\n}\n\nobject MyServiceStub {\n  def apply[F[_]: Fx: Monad](f: => Option[Id => F[Option[Name]]]): MyService[F] = new MyService[F] {\n    override def getName(id: Id): F[Option[Name]] = StubToolsFx.stub(f).flatMap(_(id))\n  }\n}\n\nclass Hello[F[_]: Monad](myService: MyService[F]) {\n  def hello(id: Id): F[String] = {\n    myService.getName(id)\n      .map { maybeName =>\n        maybeName.fold(s"No name found for id ${id.value}")(name => s"Hello ${name.value}")\n      }\n  }\n}\n\nval expectedId = Id(1)\n// expectedId: Id = 1\nval expectedName = Name("Kevin")\n// expectedName: Name = Kevin\n\nimport cats.effect._\n\nimport effectie.instances.ce2.fx._\n\nval myService: MyService[IO] = MyServiceStub(((id: Id) =>\n  if (id.value === expectedId.value)\n    pureOfSome(expectedName)\n  else\n    pureOfNone\n).some)\n// myService: MyService[IO] = repl.MdocSession$MdocApp0$MyServiceStub$$anon$1@771f7794\n')),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"val hello = new Hello[IO](myService)\n// hello: Hello[IO] = repl.MdocSession$MdocApp0$Hello@708d89d\nhello.hello(Id(1))\n  .map(println)\n  .unsafeRunSync()\n// Hello Kevin\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"hello.hello(Id(2))\n  .map(println)\n  .unsafeRunSync()\n// No name found for id 2\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"/* If you don't expect Hello to use MyService.getName,\n * you can simply remove feeding the function for that operation\n * and StubTools let you know where it fails if Hello uses MySerivce.getName.\n */\nval myService2: MyService[IO] = MyServiceStub(none)\n// myService2: MyService[IO] = repl.MdocSession$MdocApp0$MyServiceStub$$anon$1@68779e2b\nval hello2 = new Hello(myService2)\n// hello2: Hello[[A]IO[A]] = repl.MdocSession$MdocApp0$Hello@63f90c4b\nhello2.hello(Id(1))\n  .attempt\n  .map(println)\n  .unsafeRunSync()\n// Left(extras.testing.StubTools$MissingStubException: \n// >> Missing Stub implementation at\n// >>   repl.MdocSession$MdocApp0$MyServiceStub$$anon$1$$anonfun$getName$1.apply(effectie.md:54)\n// >>   ---\n// >>   Details:\n// >>   repl.MdocSession$MdocApp0$MyServiceStub$$anon$1$$anonfun$getName$1.apply(effectie.md:54)\n//        at repl.MdocSession$MdocApp0$MyServiceStub$$anon$1$$anonfun$getName$1.apply(effectie.md:54)\n//        at cats.effect.IO$.fromOption(IO.scala:1460)\n//        at effectie.instances.ce2.fxCtor$ioFxCtor$.fromOption(fxCtor.scala:28)\n//        at effectie.instances.ce2.fxCtor$ioFxCtor$.fromOption(fxCtor.scala:11)\n//        at effectie.instances.ce2.fx$ioFx$.fromOption(fx.scala:29)\n//        at effectie.instances.ce2.fx$ioFx$.fromOption(fx.scala:10)\n//        at extras.testing.StubToolsFx$StubToolsFxPartiallyApplied$.$anonfun$apply$1(StubToolsFx.scala:26)\n//        at cats.effect.internals.IORunLoop$.step(IORunLoop.scala:319)\n//        at cats.effect.IO.unsafeRunTimed(IO.scala:338)\n//        at cats.effect.IO.unsafeRunSync(IO.scala:256)\n//        at repl.MdocSession$MdocApp0$.<clinit>(effectie.md:122)\n//        at repl.MdocSession$MdocApp.<init>(effectie.md:5)\n//        at repl.MdocSession$.app(effectie.md:3)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89)\n//        at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)\n//        at scala.util.DynamicVariable.withValue(DynamicVariable.scala:59)\n//        at scala.Console$.withErr(Console.scala:193)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89)\n//        at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)\n//        at scala.util.DynamicVariable.withValue(DynamicVariable.scala:59)\n//        at scala.Console$.withOut(Console.scala:164)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88)\n//        at mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47)\n//        at mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n// )\n")),(0,a.kt)("admonition",{title:"NOTE",type:"caution"},(0,a.kt)("p",{parentName:"admonition"},"Why not just use mock framework for convenience? To answer that, please read ",(0,a.kt)("a",{parentName:"p",href:"https://www.47deg.com/blog/mocking-and-how-to-avoid-it"},"Pitfalls of Mocking in tests")," from ",(0,a.kt)("a",{parentName:"p",href:"https://www.47deg.com"},"Xebia Functional")," (formerly known as 47 Degrees)"),(0,a.kt)("p",{parentName:"admonition"},"Besides what the blog tells you, mock frameworks often make you do bad practice like testing the implementation details with ",(0,a.kt)("inlineCode",{parentName:"p"},"verify"),"."),(0,a.kt)("p",{parentName:"admonition"},"There is also an issue when your mock is not correctly set. You may get a ",(0,a.kt)("inlineCode",{parentName:"p"},"NullPointerException")," for that, but it doesn't tell you where it's from and why you get it.")))}f.isMDXComponent=!0}}]);