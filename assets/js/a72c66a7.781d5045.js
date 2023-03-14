"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[4312],{3905:(e,t,n)=>{n.d(t,{Zo:()=>p,kt:()=>f});var o=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var o=Object.getOwnPropertySymbols(e);t&&(o=o.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,o)}return n}function r(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function l(e,t){if(null==e)return{};var n,o,a=function(e,t){if(null==e)return{};var n,o,a={},i=Object.keys(e);for(o=0;o<i.length;o++)n=i[o],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(o=0;o<i.length;o++)n=i[o],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var s=o.createContext({}),c=function(e){var t=o.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):r(r({},t),e)),n},p=function(e){var t=c(e.components);return o.createElement(s.Provider,{value:t},e.children)},d="mdxType",m={inlineCode:"code",wrapper:function(e){var t=e.children;return o.createElement(o.Fragment,{},t)}},u=o.forwardRef((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,s=e.parentName,p=l(e,["components","mdxType","originalType","parentName"]),d=c(n),u=a,f=d["".concat(s,".").concat(u)]||d[u]||m[u]||i;return n?o.createElement(f,r(r({ref:t},p),{},{components:n})):o.createElement(f,r({ref:t},p))}));function f(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,r=new Array(i);r[0]=u;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[d]="string"==typeof e?e:a,r[1]=l;for(var c=2;c<i;c++)r[c]=n[c];return o.createElement.apply(null,r)}return o.createElement.apply(null,n)}u.displayName="MDXCreateElement"},4529:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>r,default:()=>m,frontMatter:()=>i,metadata:()=>l,toc:()=>c});var o=n(7462),a=(n(7294),n(3905));const i={sidebar_position:2,sidebar_label:"Core",id:"core",title:"Extras - Testing Tools Core"},r=void 0,l={unversionedId:"extras-testing-tools/core",id:"extras-testing-tools/core",title:"Extras - Testing Tools Core",description:"StubTools.missing",source:"@site/../generated-docs/docs/extras-testing-tools/testing-tools.md",sourceDirName:"extras-testing-tools",slug:"/extras-testing-tools/core",permalink:"/docs/extras-testing-tools/core",draft:!1,tags:[],version:"current",sidebarPosition:2,frontMatter:{sidebar_position:2,sidebar_label:"Core",id:"core",title:"Extras - Testing Tools Core"},sidebar:"tutorialSidebar",previous:{title:"Getting Started",permalink:"/docs/extras-testing-tools/"},next:{title:"Cats",permalink:"/docs/extras-testing-tools/cats/"}},s={},c=[{value:"<code>StubTools.missing</code>",id:"stubtoolsmissing",level:2}],p={toc:c},d="wrapper";function m(e){let{components:t,...n}=e;return(0,a.kt)(d,(0,o.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"stubtoolsmissing"},(0,a.kt)("inlineCode",{parentName:"h2"},"StubTools.missing")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"StubTools.missing")," is a tool for a stub (a simple function for testing) so that you don't need to use mock frameworks."),(0,a.kt)("admonition",{title:"NOTE",type:"info"},(0,a.kt)("p",{parentName:"admonition"},"In most cases, you probably don't want to use ",(0,a.kt)("inlineCode",{parentName:"p"},"StubTools.missing")," directly. It is recommended to use ",(0,a.kt)("a",{parentName:"p",href:"cats"},"StubToolsCats")," or ",(0,a.kt)("a",{parentName:"p",href:"effectie"},"StubToolsFx")," instead.")),(0,a.kt)("p",null,"e.g.)"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.syntax.all._\nimport eu.timepit.refined.types.all._\nimport eu.timepit.refined.cats._\nimport eu.timepit.refined.auto._\nimport io.estatico.newtype.macros.newtype\nimport extras.testing.StubTools\n\nobject types {\n  @newtype case class Id(value: PosInt)\n  @newtype case class Name(value: NonEmptyString)\n}\nimport types._\n\ntrait MyService {\n  def findName(id: Id): Option[Name]\n}\n\nobject MyServiceStub {\n  def apply(f: => Option[Id => Option[Name]]): MyService = new MyService {\n    override def findName(id: Id): Option[Name] = f.fold[Option[Name]](throw StubTools.missing)(_(id))\n  }\n}\n\nclass Hello(myService: MyService) {\n  def hello(id: Id): String = {\n    myService.findName(id)\n    .fold(s"No name found for id ${id.value}") { name =>\n      s"Hello ${name.value}"\n    }\n  }\n}\n\nval expectedId = Id(1)\n// expectedId: Id = 1\nval expectedName = Name("Kevin")\n// expectedName: Name = Kevin\n\nval myService: MyService = MyServiceStub(((id: Id) =>\n  if (id.value === expectedId.value)\n    expectedName.some\n  else\n    none\n).some)\n// myService: MyService = repl.MdocSession$MdocApp0$MyServiceStub$$anon$1@60270bfe\n')),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"val hello = new Hello(myService)\n// hello: Hello = repl.MdocSession$MdocApp0$Hello@39beb13e\nprintln(hello.hello(Id(1)))\n// Hello Kevin\nprintln(hello.hello(Id(2)))\n// No name found for id 2\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"/* If you don't expect Hello to use MyService.findName,\n * you can simply remove feeding the function for that operation\n * and StubTools let you know where it fails if Hello uses MySerivce.findName.\n */\nval myService2: MyService = MyServiceStub(none)\nval hello2 = new Hello(myService2)\nprintln(hello2.hello(Id(1)))\n// extras.testing.StubTools$MissingStubException: \n// >> Missing Stub implementation at\n// >>   repl.MdocSession$MdocApp0$MyServiceStub$$anon$1$$anonfun$findName$1.apply(testing-tools.md:45)\n// >>   ---\n// >>   Details:\n// >>   repl.MdocSession$MdocApp0$MyServiceStub$$anon$1$$anonfun$findName$1.apply(testing-tools.md:45)\n//        at repl.MdocSession$MdocApp0$MyServiceStub$$anon$1$$anonfun$findName$1.apply(testing-tools.md:45)\n//        at scala.Option.fold(Option.scala:263)\n//        at repl.MdocSession$MdocApp0$MyServiceStub$$anon$1.findName(testing-tools.md:45)\n//        at repl.MdocSession$MdocApp0$Hello.hello(testing-tools.md:52)\n//        at repl.MdocSession$MdocApp0$$anonfun$2.apply$mcV$sp(testing-tools.md:94)\n//        at repl.MdocSession$MdocApp0$$anonfun$2.apply(testing-tools.md:91)\n//        at repl.MdocSession$MdocApp0$$anonfun$2.apply(testing-tools.md:91)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.crash(DocumentBuilder.scala:75)\n//        at repl.MdocSession$MdocApp0$.<clinit>(testing-tools.md:91)\n//        at repl.MdocSession$MdocApp.<init>(testing-tools.md:5)\n//        at repl.MdocSession$.app(testing-tools.md:3)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$2(DocumentBuilder.scala:89)\n//        at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)\n//        at scala.util.DynamicVariable.withValue(DynamicVariable.scala:59)\n//        at scala.Console$.withErr(Console.scala:193)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.$anonfun$build$1(DocumentBuilder.scala:89)\n//        at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)\n//        at scala.util.DynamicVariable.withValue(DynamicVariable.scala:59)\n//        at scala.Console$.withOut(Console.scala:164)\n//        at mdoc.internal.document.DocumentBuilder$$doc$.build(DocumentBuilder.scala:88)\n//        at mdoc.internal.markdown.MarkdownBuilder$.$anonfun$buildDocument$2(MarkdownBuilder.scala:47)\n//        at mdoc.internal.markdown.MarkdownBuilder$$anon$1.run(MarkdownBuilder.scala:104)\n// \n")),(0,a.kt)("admonition",{title:"NOTE",type:"caution"},(0,a.kt)("p",{parentName:"admonition"},"Why not just use mock framework for convenience? To answer that, please read ",(0,a.kt)("a",{parentName:"p",href:"https://www.47deg.com/blog/mocking-and-how-to-avoid-it"},"Pitfalls of Mocking in tests")," from ",(0,a.kt)("a",{parentName:"p",href:"https://www.47deg.com"},"Xebia Functional")," (formerly known as 47 Degrees)"),(0,a.kt)("p",{parentName:"admonition"},"Besides what the blog tells you, mock frameworks often make you do bad practice like testing the implementation details with ",(0,a.kt)("inlineCode",{parentName:"p"},"verify"),"."),(0,a.kt)("p",{parentName:"admonition"},"There is also an issue when your mock is not correctly set. You may get a ",(0,a.kt)("inlineCode",{parentName:"p"},"NullPointerException")," for that, but it doesn't tell you where it's from and why you get it.")))}m.isMDXComponent=!0}}]);