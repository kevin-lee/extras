"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[1103],{3905:(e,n,t)=>{t.d(n,{Zo:()=>u,kt:()=>g});var a=t(7294);function s(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function r(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);n&&(a=a.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,a)}return t}function o(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?r(Object(t),!0).forEach((function(n){s(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):r(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function l(e,n){if(null==e)return{};var t,a,s=function(e,n){if(null==e)return{};var t,a,s={},r=Object.keys(e);for(a=0;a<r.length;a++)t=r[a],n.indexOf(t)>=0||(s[t]=e[t]);return s}(e,n);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(a=0;a<r.length;a++)t=r[a],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(s[t]=e[t])}return s}var i=a.createContext({}),p=function(e){var n=a.useContext(i),t=n;return e&&(t="function"==typeof e?e(n):o(o({},n),e)),t},u=function(e){var n=p(e.components);return a.createElement(i.Provider,{value:n},e.children)},d="mdxType",c={inlineCode:"code",wrapper:function(e){var n=e.children;return a.createElement(a.Fragment,{},n)}},m=a.forwardRef((function(e,n){var t=e.components,s=e.mdxType,r=e.originalType,i=e.parentName,u=l(e,["components","mdxType","originalType","parentName"]),d=p(t),m=s,g=d["".concat(i,".").concat(m)]||d[m]||c[m]||r;return t?a.createElement(g,o(o({ref:n},u),{},{components:t})):a.createElement(g,o({ref:n},u))}));function g(e,n){var t=arguments,s=n&&n.mdxType;if("string"==typeof e||s){var r=t.length,o=new Array(r);o[0]=m;var l={};for(var i in n)hasOwnProperty.call(n,i)&&(l[i]=n[i]);l.originalType=e,l[d]="string"==typeof e?e:s,o[1]=l;for(var p=2;p<r;p++)o[p]=t[p];return a.createElement.apply(null,o)}return a.createElement.apply(null,t)}m.displayName="MDXCreateElement"},3018:(e,n,t)=>{t.r(n),t.d(n,{assets:()=>i,contentTitle:()=>o,default:()=>c,frontMatter:()=>r,metadata:()=>l,toc:()=>p});var a=t(7462),s=(t(7294),t(3905));const r={sidebar_position:2,id:"syntax",title:"Reflects Syntax"},o=void 0,l={unversionedId:"extras-reflects/syntax",id:"extras-reflects/syntax",title:"Reflects Syntax",description:"reflects syntax for WeakTypeTag",source:"@site/../generated-docs/docs/extras-reflects/syntax.md",sourceDirName:"extras-reflects",slug:"/extras-reflects/syntax",permalink:"/docs/extras-reflects/syntax",draft:!1,tags:[],version:"current",sidebarPosition:2,frontMatter:{sidebar_position:2,id:"syntax",title:"Reflects Syntax"},sidebar:"tutorialSidebar",previous:{title:"Getting Started",permalink:"/docs/extras-reflects/"}},i={},p=[{value:"<code>reflects</code> syntax for <code>WeakTypeTag</code>",id:"reflects-syntax-for-weaktypetag",level:2},{value:"<code>value.nestedTypeName</code>",id:"valuenestedtypename",level:3},{value:"<code>WeakTypeTag[A].nestedTypeName</code>",id:"weaktypetaganestedtypename",level:3},{value:"Works for <code>@newtype</code>",id:"works-for-newtype",level:3},{value:"<code>reflects</code> syntax for <code>ClassTag</code>",id:"reflects-syntax-for-classtag",level:2},{value:"<code>value.nestedRuntimeClassName</code>",id:"valuenestedruntimeclassname",level:3},{value:"<code>ClassTag[A].nestedRuntimeClassName</code>",id:"classtaganestedruntimeclassname",level:3},{value:"Do not use for <code>@newtype</code>",id:"do-not-use-for-newtype",level:3},{value:"<code>reflects</code> syntax for <code>Class</code>",id:"reflects-syntax-for-class",level:2},{value:"<code>value.nestedClassName</code>",id:"valuenestedclassname",level:3},{value:"<code>Class[A].nestedClassName</code>",id:"classanestedclassname",level:3},{value:"Do not use for <code>@newtype</code>",id:"do-not-use-for-newtype-1",level:3}],u={toc:p},d="wrapper";function c(e){let{components:n,...t}=e;return(0,s.kt)(d,(0,a.Z)({},u,t,{components:n,mdxType:"MDXLayout"}),(0,s.kt)("h2",{id:"reflects-syntax-for-weaktypetag"},(0,s.kt)("inlineCode",{parentName:"h2"},"reflects")," syntax for ",(0,s.kt)("inlineCode",{parentName:"h2"},"WeakTypeTag")),(0,s.kt)("h3",{id:"valuenestedtypename"},(0,s.kt)("inlineCode",{parentName:"h3"},"value.nestedTypeName")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import java.time._\n\nsealed trait Status\nobject Status {\n  final case class InProgress(startedAt: Instant) extends Status\n  case object Done extends Status\n  \n  def inProgress(startedAt: Instant): Status = InProgress(startedAt)\n  def done: Status = Done\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import scala.reflect.runtime.universe._\nimport extras.reflects.syntax.tags._\n\ndef infoWithWeakTypeTag[A: WeakTypeTag](a: A): Unit =\n  println(\n    s"""value: $a\n       | type: ${weakTypeTag[A].nestedTypeName}\n       |""".stripMargin)\n\ninfoWithWeakTypeTag(Status.inProgress(Instant.now()))\n// value: InProgress(2023-02-26T09:08:41.144343Z)\n//  type: MdocApp0.Status\n// \ninfoWithWeakTypeTag(Status.InProgress(Instant.now()))\n// value: InProgress(2023-02-26T09:08:41.184619Z)\n//  type: Status.InProgress\n// \n\ninfoWithWeakTypeTag(Status.done)\n// value: Done\n//  type: MdocApp0.Status\n// \ninfoWithWeakTypeTag(Status.Done)\n// value: Done\n//  type: Status.Done\n//\n')),(0,s.kt)("h3",{id:"weaktypetaganestedtypename"},(0,s.kt)("inlineCode",{parentName:"h3"},"WeakTypeTag[A].nestedTypeName")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import java.time._\n\nsealed trait Status\nobject Status {\n  final case class InProgress(startedAt: Instant) extends Status\n  case object Done extends Status\n  \n  def inProgress(startedAt: Instant): Status = InProgress(startedAt)\n  def done: Status = Done\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import scala.reflect.runtime.universe._\nimport extras.reflects.syntax.tags._\n\ndef infoWithWeakTypeTag[A](implicit weakTypeTag: WeakTypeTag[A]): Unit =\n  println(\n    s"""type: ${weakTypeTag.nestedTypeName}\n       |""".stripMargin)\n\ninfoWithWeakTypeTag[Status.InProgress]\n// type: Status.InProgress\n// \ninfoWithWeakTypeTag[Status.Done.type]\n// type: Status.Done\n// \n\nprintln(weakTypeTag[Status.InProgress].nestedTypeName)\n// Status.InProgress\nprintln(weakTypeTag[Status.Done.type].nestedTypeName)\n// Status.Done\n')),(0,s.kt)("h3",{id:"works-for-newtype"},"Works for ",(0,s.kt)("inlineCode",{parentName:"h3"},"@newtype")),(0,s.kt)("admonition",{type:"info"},(0,s.kt)("mdxAdmonitionTitle",{parentName:"admonition"},"It works for ",(0,s.kt)("inlineCode",{parentName:"mdxAdmonitionTitle"},"newtype")," as well."),(0,s.kt)("p",{parentName:"admonition"},"If you use ",(0,s.kt)("a",{parentName:"p",href:"https://github.com/estatico/scala-newtype"},"newtype")," and want to get the ",(0,s.kt)("inlineCode",{parentName:"p"},"newtype")," name, ",(0,s.kt)("inlineCode",{parentName:"p"},"WeakTypeTag")," syntax is what you should use since you can get the name of ",(0,s.kt)("inlineCode",{parentName:"p"},"newtype")," with it.")),(0,s.kt)("p",null,"An example showing that it works with ",(0,s.kt)("inlineCode",{parentName:"p"},"@newtype"),":"),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import io.estatico.newtype.macros.newtype\n\nobject Types {\n  @newtype case class Id(value: Long)\n  @newtype case class Username(value: String)\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import scala.reflect.runtime.universe._\nimport extras.reflects.syntax.tags._\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'def infoWithClassTag[A](a: A)(implicit weakTypeTag: WeakTypeTag[A]): Unit =\n  println(\n    s"""value: $a\n       | type: ${weakTypeTag.nestedTypeName}\n       |""".stripMargin)\n\nimport Types._\n\ninfoWithClassTag(Id(1L))\n// value: 1\n//  type: Types.Id\n// \ninfoWithClassTag(Username("someuser"))\n// value: someuser\n//  type: Types.Username\n// \n\nprintln(weakTypeTag[Id].nestedTypeName)\n// Types.Id\nprintln(weakTypeTag[Username].nestedTypeName)\n// Types.Username\n')),(0,s.kt)("h2",{id:"reflects-syntax-for-classtag"},(0,s.kt)("inlineCode",{parentName:"h2"},"reflects")," syntax for ",(0,s.kt)("inlineCode",{parentName:"h2"},"ClassTag")),(0,s.kt)("h3",{id:"valuenestedruntimeclassname"},(0,s.kt)("inlineCode",{parentName:"h3"},"value.nestedRuntimeClassName")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import java.time._\n\nsealed trait Status\nobject Status {\n  final case class InProgress(startedAt: Instant) extends Status\n  case object Done extends Status\n  \n  def inProgress(startedAt: Instant): Status = InProgress(startedAt)\n  def done: Status = Done\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.reflects.syntax.tags._\n\ndef infoWithClassTag[A](a: A): Unit =\n  println(\n    s"""value: $a\n       | type: ${a.nestedRuntimeClassName}\n       |""".stripMargin)\n\ninfoWithClassTag(Status.inProgress(Instant.now()))\n// value: InProgress(2023-02-26T09:08:41.201095Z)\n//  type: Status.InProgress\n// \ninfoWithClassTag(Status.InProgress(Instant.now()))\n// value: InProgress(2023-02-26T09:08:41.202972Z)\n//  type: Status.InProgress\n// \n\ninfoWithClassTag(Status.done)\n// value: Done\n//  type: Status.Done\n// \ninfoWithClassTag(Status.Done)\n// value: Done\n//  type: Status.Done\n//\n')),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"println(Status.inProgress(Instant.now()).nestedRuntimeClassName)\n// Status.InProgress\nprintln(Status.InProgress(Instant.now()).nestedRuntimeClassName)\n// Status.InProgress\n\nprintln(Status.done.nestedRuntimeClassName)\n// Status.Done\nprintln(Status.Done.nestedRuntimeClassName)\n// Status.Done\n")),(0,s.kt)("h3",{id:"classtaganestedruntimeclassname"},(0,s.kt)("inlineCode",{parentName:"h3"},"ClassTag[A].nestedRuntimeClassName")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import java.time._\n\nsealed trait Status\nobject Status {\n  final case class InProgress(startedAt: Instant) extends Status\n  case object Done extends Status\n  \n  def inProgress(startedAt: Instant): Status = InProgress(startedAt)\n  def done: Status = Done\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import scala.reflect.{classTag, ClassTag}\nimport extras.reflects.syntax.tags._\n\ndef infoWithClassTag[A](implicit classTag: ClassTag[A]): Unit =\n  println(\n    s"""type: ${classTag.nestedRuntimeClassName}\n       |""".stripMargin)\n\ninfoWithClassTag[Status.InProgress]\n// type: Status.InProgress\n// \ninfoWithClassTag[Status.Done.type]\n// type: Status.Done\n//\n')),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"println(classTag[Status.InProgress].nestedRuntimeClassName)\n// Status.InProgress\nprintln(classTag[Status.Done.type].nestedRuntimeClassName)\n// Status.Done\n")),(0,s.kt)("h3",{id:"do-not-use-for-newtype"},"Do not use for ",(0,s.kt)("inlineCode",{parentName:"h3"},"@newtype")),(0,s.kt)("admonition",{type:"caution"},(0,s.kt)("mdxAdmonitionTitle",{parentName:"admonition"},"Do not use it for ",(0,s.kt)("inlineCode",{parentName:"mdxAdmonitionTitle"},"newtype"),"."),(0,s.kt)("p",{parentName:"admonition"},"If you use ",(0,s.kt)("a",{parentName:"p",href:"https://github.com/estatico/scala-newtype"},"newtype")," and want to get the ",(0,s.kt)("inlineCode",{parentName:"p"},"newtype")," name, ",(0,s.kt)("inlineCode",{parentName:"p"},"ClassTag")," syntax is not the one you should use since you can get only the actual type not ",(0,s.kt)("inlineCode",{parentName:"p"},"newtype"),". For ",(0,s.kt)("inlineCode",{parentName:"p"},"@newtype"),", please use '",(0,s.kt)("a",{parentName:"p",href:"#works-for-newtype"},(0,s.kt)("inlineCode",{parentName:"a"},"reflects")," syntax for ",(0,s.kt)("inlineCode",{parentName:"a"},"WeakTypeTag")),"'.")),(0,s.kt)("p",null,"An example showing that it does not work with ",(0,s.kt)("inlineCode",{parentName:"p"},"@newtype"),":"),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import io.estatico.newtype.macros.newtype\n\nobject Types {\n  @newtype case class Id(value: Long)\n  @newtype case class Username(value: String)\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.reflects.syntax.tags._\n\ndef infoWithClassTag[A](a: A): Unit =\n  println(\n    s"""value: $a\n       | type: ${a.nestedRuntimeClassName}\n       |""".stripMargin)\n\nimport Types._\n\ninfoWithClassTag(Id(1L))\n// value: 1\n//  type: lang.Long\n// \ninfoWithClassTag(Username("someuser"))\n// value: someuser\n//  type: lang.String\n// \n\nprintln(Id(1L).nestedRuntimeClassName)\n// lang.Long\nprintln(Username("someuser").nestedRuntimeClassName)\n// lang.String\n')),(0,s.kt)("h2",{id:"reflects-syntax-for-class"},(0,s.kt)("inlineCode",{parentName:"h2"},"reflects")," syntax for ",(0,s.kt)("inlineCode",{parentName:"h2"},"Class")),(0,s.kt)("h3",{id:"valuenestedclassname"},(0,s.kt)("inlineCode",{parentName:"h3"},"value.nestedClassName")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import java.time._\n\nsealed trait Status\nobject Status {\n  final case class InProgress(startedAt: Instant) extends Status\n  case object Done extends Status\n  \n  def inProgress(startedAt: Instant): Status = InProgress(startedAt)\n  def done: Status = Done\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.reflects.syntax.classes._\n\ndef infoWithClass[A](a: A): Unit =\n  println(\n    s"""value: $a\n       | type: ${a.nestedClassName}\n       |""".stripMargin)\n\ninfoWithClass(Status.inProgress(Instant.now()))\n// value: InProgress(2023-02-26T09:08:41.211669Z)\n//  type: Status.InProgress\n// \ninfoWithClass(Status.InProgress(Instant.now()))\n// value: InProgress(2023-02-26T09:08:41.212822Z)\n//  type: Status.InProgress\n// \n\ninfoWithClass(Status.done)\n// value: Done\n//  type: Status.Done\n// \ninfoWithClass(Status.Done)\n// value: Done\n//  type: Status.Done\n//\n')),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"println(Status.inProgress(Instant.now()).nestedClassName)\n// Status.InProgress\nprintln(Status.InProgress(Instant.now()).nestedClassName)\n// Status.InProgress\n\nprintln(Status.done.nestedClassName)\n// Status.Done\nprintln(Status.Done.nestedClassName)\n// Status.Done\n")),(0,s.kt)("h3",{id:"classanestedclassname"},(0,s.kt)("inlineCode",{parentName:"h3"},"Class[A].nestedClassName")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import java.time._\n\nsealed trait Status\nobject Status {\n  final case class InProgress(startedAt: Instant) extends Status\n  case object Done extends Status\n  \n  def inProgress(startedAt: Instant): Status = InProgress(startedAt)\n  def done: Status = Done\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.reflects.syntax.classes._\n\ndef infoWithClass[A](aClass: Class[A]): Unit =\n  println(\n    s"""type: ${aClass.nestedClassName}\n       |""".stripMargin)\n\ninfoWithClass(Status.InProgress.getClass)\n// type: Status.InProgress\n// \ninfoWithClass(Status.Done.getClass)\n// type: Status.Done\n//\n')),(0,s.kt)("h3",{id:"do-not-use-for-newtype-1"},"Do not use for ",(0,s.kt)("inlineCode",{parentName:"h3"},"@newtype")),(0,s.kt)("admonition",{type:"caution"},(0,s.kt)("mdxAdmonitionTitle",{parentName:"admonition"},"Do not use it for ",(0,s.kt)("inlineCode",{parentName:"mdxAdmonitionTitle"},"newtype"),"."),(0,s.kt)("p",{parentName:"admonition"},"If you use ",(0,s.kt)("a",{parentName:"p",href:"https://github.com/estatico/scala-newtype"},"newtype")," and want to get the ",(0,s.kt)("inlineCode",{parentName:"p"},"newtype")," name, ",(0,s.kt)("inlineCode",{parentName:"p"},"Class")," syntax is not the one you should use since you can get only the actual type not ",(0,s.kt)("inlineCode",{parentName:"p"},"newtype"),". For ",(0,s.kt)("inlineCode",{parentName:"p"},"@newtype"),", please use '",(0,s.kt)("a",{parentName:"p",href:"#works-for-newtype"},(0,s.kt)("inlineCode",{parentName:"a"},"reflects")," syntax for ",(0,s.kt)("inlineCode",{parentName:"a"},"WeakTypeTag")),"'.")),(0,s.kt)("p",null,"An example showing that it does not work with ",(0,s.kt)("inlineCode",{parentName:"p"},"@newtype"),":"),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},"import io.estatico.newtype.macros.newtype\n\nobject Types {\n  @newtype case class Id(value: Long)\n  @newtype case class Username(value: String)\n}\n")),(0,s.kt)("pre",null,(0,s.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.reflects.syntax.classes._\n\ndef infoWithClassTag[A](a: A): Unit =\n  println(\n    s"""value: $a\n       | type: ${a.nestedClassName}\n       |""".stripMargin)\n\nimport Types._\n\ninfoWithClassTag(Id(1L))\n// value: 1\n//  type: lang.Long\n// \ninfoWithClassTag(Username("someuser"))\n// value: someuser\n//  type: lang.String\n// \n\nprintln(Id(1L).nestedClassName)\n// lang.Long\nprintln(Username("someuser").nestedClassName)\n// lang.String\n')))}c.isMDXComponent=!0}}]);