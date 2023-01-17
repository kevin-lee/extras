"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[2442],{3905:(t,e,r)=>{r.d(e,{Zo:()=>c,kt:()=>m});var n=r(7294);function a(t,e,r){return e in t?Object.defineProperty(t,e,{value:r,enumerable:!0,configurable:!0,writable:!0}):t[e]=r,t}function s(t,e){var r=Object.keys(t);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(t);e&&(n=n.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),r.push.apply(r,n)}return r}function o(t){for(var e=1;e<arguments.length;e++){var r=null!=arguments[e]?arguments[e]:{};e%2?s(Object(r),!0).forEach((function(e){a(t,e,r[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(r)):s(Object(r)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(r,e))}))}return t}function i(t,e){if(null==t)return{};var r,n,a=function(t,e){if(null==t)return{};var r,n,a={},s=Object.keys(t);for(n=0;n<s.length;n++)r=s[n],e.indexOf(r)>=0||(a[r]=t[r]);return a}(t,e);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(t);for(n=0;n<s.length;n++)r=s[n],e.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(t,r)&&(a[r]=t[r])}return a}var l=n.createContext({}),p=function(t){var e=n.useContext(l),r=e;return t&&(r="function"==typeof t?t(e):o(o({},e),t)),r},c=function(t){var e=p(t.components);return n.createElement(l.Provider,{value:e},t.children)},f={inlineCode:"code",wrapper:function(t){var e=t.children;return n.createElement(n.Fragment,{},e)}},u=n.forwardRef((function(t,e){var r=t.components,a=t.mdxType,s=t.originalType,l=t.parentName,c=i(t,["components","mdxType","originalType","parentName"]),u=p(r),m=a,d=u["".concat(l,".").concat(m)]||u[m]||f[m]||s;return r?n.createElement(d,o(o({ref:e},c),{},{components:r})):n.createElement(d,o({ref:e},c))}));function m(t,e){var r=arguments,a=e&&e.mdxType;if("string"==typeof t||a){var s=r.length,o=new Array(s);o[0]=u;var i={};for(var l in e)hasOwnProperty.call(e,l)&&(i[l]=e[l]);i.originalType=t,i.mdxType="string"==typeof t?t:a,o[1]=i;for(var p=2;p<s;p++)o[p]=r[p];return n.createElement.apply(null,o)}return n.createElement.apply(null,r)}u.displayName="MDXCreateElement"},4165:(t,e,r)=>{r.r(e),r.d(e,{assets:()=>l,contentTitle:()=>o,default:()=>f,frontMatter:()=>s,metadata:()=>i,toc:()=>p});var n=r(7462),a=(r(7294),r(3905));const s={sidebar_position:1,sidebar_label:"fs2-v2-text",id:"extras-fs2-v2-text",title:"Fs2 v2 text"},o=void 0,i={unversionedId:"extras-fs2/v2/extras-fs2-v2-text",id:"extras-fs2/v2/extras-fs2-v2-text",title:"Fs2 v2 text",description:"Get extras-fs2-v2-text",source:"@site/../generated-docs/docs/extras-fs2/v2/text.md",sourceDirName:"extras-fs2/v2",slug:"/extras-fs2/v2/extras-fs2-v2-text",permalink:"/docs/extras-fs2/v2/extras-fs2-v2-text",draft:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,sidebar_label:"fs2-v2-text",id:"extras-fs2-v2-text",title:"Fs2 v2 text"},sidebar:"tutorialSidebar",previous:{title:"v2",permalink:"/docs/category/v2"},next:{title:"v3",permalink:"/docs/category/v3"}},l={},p=[{value:"Get <code>extras-fs2-v2-text</code>",id:"get-extras-fs2-v2-text",level:2},{value:"StreamF, Byte.utf8String",id:"streamf-byteutf8string",level:2},{value:"utf8String for http4s",id:"utf8string-for-http4s",level:2}],c={toc:p};function f(t){let{components:e,...r}=t;return(0,a.kt)("wrapper",(0,n.Z)({},c,r,{components:e,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"get-extras-fs2-v2-text"},"Get ",(0,a.kt)("inlineCode",{parentName:"h2"},"extras-fs2-v2-text")),(0,a.kt)("p",null,"It's only for Fs2 v2 so if you use v3, please use ",(0,a.kt)("a",{parentName:"p",href:"../v3/extras-fs2-v3-text"},"extras-fs2-v3-text"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-fs2-v2-text" % "0.27.0"\n')),(0,a.kt)("h2",{id:"streamf-byteutf8string"},"Stream","[F, Byte]",".utf8String"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"Stream[F, Byte].utf8String // F[String]\n")),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.effect._\nimport extras.fs2.text.syntax._\nimport fs2.Stream\n\nimport java.nio.charset.StandardCharsets\n\nStream[IO, Byte]("blah blah".getBytes(StandardCharsets.UTF_8).toList: _*)\n  .utf8String // IO[String]\n  .unsafeRunSync()\n// res1: String = "blah blah"\n')),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'import cats.effect._\nimport fs2.Stream\n\nimport java.nio.charset.StandardCharsets\n\ndef toByteStream[F[*]](s: String): Stream[F, Byte] =\n  Stream[F, Byte](s.getBytes(StandardCharsets.UTF_8).toList: _*)\n\ndef bytesToString[F[*]: Sync](stream: Stream[F, Byte]): F[String] = {\n  import extras.fs2.text.syntax._\n  stream.utf8String\n}\n\nval s = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."\n// s: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."\nval byteStream = toByteStream[IO](s)\n// byteStream: Stream[IO, Byte] = Stream(..)\n\nbytesToString[IO](byteStream)\n  .unsafeRunSync()\n// res3: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."\n')),(0,a.kt)("h2",{id:"utf8string-for-http4s"},"utf8String for http4s"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'import org.http4s.dsl.io._\nval response = Ok("Hello world!")\n// response: cats.effect.IO[org.http4s.Response[cats.effect.IO]] = Pure(\n//   a = (\n//      = Status(code = 200),\n//      = HttpVersion(major = 1, minor = 1),\n//      = Headers(Content-Type: text/plain; charset=UTF-8, Content-Length: 12),\n//      = Stream(..),\n//      = org.typelevel.vault.Vault@1a5d1067\n//   )\n// )\nval responseToString = response\n  .flatMap { response =>\n    import extras.fs2.text.syntax._\n    response\n      .body\n      .utf8String\n  }\n// responseToString: cats.effect.IO[String] = Bind(\n//   source = Pure(\n//     a = (\n//        = Status(code = 200),\n//        = HttpVersion(major = 1, minor = 1),\n//        = Headers(Content-Type: text/plain; charset=UTF-8, Content-Length: 12),\n//        = Stream(..),\n//        = org.typelevel.vault.Vault@1a5d1067\n// ...\nresponseToString.unsafeRunSync()\n// res5: String = "Hello world!"\n')))}f.isMDXComponent=!0}}]);