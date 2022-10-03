"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[53],{3905:(n,e,a)=>{a.d(e,{Zo:()=>f,kt:()=>u});var r=a(7294);function t(n,e,a){return e in n?Object.defineProperty(n,e,{value:a,enumerable:!0,configurable:!0,writable:!0}):n[e]=a,n}function l(n,e){var a=Object.keys(n);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(n);e&&(r=r.filter((function(e){return Object.getOwnPropertyDescriptor(n,e).enumerable}))),a.push.apply(a,r)}return a}function o(n){for(var e=1;e<arguments.length;e++){var a=null!=arguments[e]?arguments[e]:{};e%2?l(Object(a),!0).forEach((function(e){t(n,e,a[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(n,Object.getOwnPropertyDescriptors(a)):l(Object(a)).forEach((function(e){Object.defineProperty(n,e,Object.getOwnPropertyDescriptor(a,e))}))}return n}function g(n,e){if(null==n)return{};var a,r,t=function(n,e){if(null==n)return{};var a,r,t={},l=Object.keys(n);for(r=0;r<l.length;r++)a=l[r],e.indexOf(a)>=0||(t[a]=n[a]);return t}(n,e);if(Object.getOwnPropertySymbols){var l=Object.getOwnPropertySymbols(n);for(r=0;r<l.length;r++)a=l[r],e.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(n,a)&&(t[a]=n[a])}return t}var s=r.createContext({}),i=function(n){var e=r.useContext(s),a=e;return n&&(a="function"==typeof n?n(e):o(o({},e),n)),a},f=function(n){var e=i(n.components);return r.createElement(s.Provider,{value:e},n.children)},c={inlineCode:"code",wrapper:function(n){var e=n.children;return r.createElement(r.Fragment,{},e)}},b=r.forwardRef((function(n,e){var a=n.components,t=n.mdxType,l=n.originalType,s=n.parentName,f=g(n,["components","mdxType","originalType","parentName"]),b=i(a),u=t,p=b["".concat(s,".").concat(u)]||b[u]||c[u]||l;return a?r.createElement(p,o(o({ref:e},f),{},{components:a})):r.createElement(p,o({ref:e},f))}));function u(n,e){var a=arguments,t=e&&e.mdxType;if("string"==typeof n||t){var l=a.length,o=new Array(l);o[0]=b;var g={};for(var s in e)hasOwnProperty.call(e,s)&&(g[s]=e[s]);g.originalType=n,g.mdxType="string"==typeof n?n:t,o[1]=g;for(var i=2;i<l;i++)o[i]=a[i];return r.createElement.apply(null,o)}return r.createElement.apply(null,a)}b.displayName="MDXCreateElement"},887:(n,e,a)=>{a.r(e),a.d(e,{assets:()=>s,contentTitle:()=>o,default:()=>c,frontMatter:()=>l,metadata:()=>g,toc:()=>i});var r=a(7462),t=(a(7294),a(3905));const l={sidebar_position:1,id:"rgb",title:"Rgb"},o="Rgb",g={unversionedId:"extras-scala-io/truecolor/rgb",id:"extras-scala-io/truecolor/rgb",title:"Rgb",description:"Rgb",source:"@site/../generated-docs/docs/extras-scala-io/truecolor/rgb.md",sourceDirName:"extras-scala-io/truecolor",slug:"/extras-scala-io/truecolor/rgb",permalink:"/docs/extras-scala-io/truecolor/rgb",draft:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,id:"rgb",title:"Rgb"},sidebar:"tutorialSidebar",previous:{title:"True Color",permalink:"/docs/category/true-color"},next:{title:"File",permalink:"/docs/extras-scala-io/file"}},s={},i=[{value:"<code>Rgb</code>",id:"rgb-1",level:2},{value:"<code>Rgb.fromInt(Int)</code>",id:"rgbfromintint",level:2},{value:"<code>Rgb.fromInt(Int)</code> - Valid",id:"rgbfromintint---valid",level:3},{value:"<code>Rgb.fromInt(Int)</code> - Invalid",id:"rgbfromintint---invalid",level:3},{value:"<code>Rgb.unsafeFromInt(Int)</code>",id:"rgbunsafefromintint",level:2},{value:"<code>Rgb.unsafeFromInt(Int)</code> - Valid",id:"rgbunsafefromintint---valid",level:3},{value:"<code>Rgb.unsafeFromInt(Int)</code> - Invalid",id:"rgbunsafefromintint---invalid",level:3},{value:"<code>Rgb.fromHexString(String)</code>",id:"rgbfromhexstringstring",level:2},{value:"<code>Rgb.fromHexString(String)</code> - Valid",id:"rgbfromhexstringstring---valid",level:3},{value:"<code>Rgb.fromHexString(String)</code> - Invalid",id:"rgbfromhexstringstring---invalid",level:3},{value:"<code>Rgb.unsafeFromHexString(String)</code>",id:"rgbunsafefromhexstringstring",level:2},{value:"<code>Rgb.unsafeFromHexString(String)</code> - Valid",id:"rgbunsafefromhexstringstring---valid",level:3},{value:"<code>Rgb.unsafeFromHexString(String)</code> - Invalid",id:"rgbunsafefromhexstringstring---invalid",level:3},{value:"To Colored String Form",id:"to-colored-string-form",level:2},{value:"<code>Rgb.toAsciiEsc</code>",id:"rgbtoasciiesc",level:3},{value:"<code>Rgb.toHex</code>",id:"rgbtohex",level:3},{value:"<code>Rgb.toHexHtml</code>",id:"rgbtohexhtml",level:3},{value:"<code>Rgb.toRgbInts</code>",id:"rgbtorgbints",level:3},{value:"<code>Rgb.color(String)</code>",id:"rgbcolorstring",level:3},{value:"<code>Rgb.colored(String)</code>",id:"rgbcoloredstring",level:3},{value:"syntax",id:"syntax",level:2},{value:"<code>String.rgb(Int)</code> and <code>String.rgbed(Int)</code>",id:"stringrgbint-and-stringrgbedint",level:3},{value:"Invalid RGB Value Handling",id:"invalid-rgb-value-handling",level:3},{value:"<code>String.rgb(Rgb)</code> and <code>String.rgbed(Rgb)</code>",id:"stringrgbrgb-and-stringrgbedrgb",level:3}],f={toc:i};function c(n){let{components:e,...a}=n;return(0,t.kt)("wrapper",(0,r.Z)({},f,a,{components:e,mdxType:"MDXLayout"}),(0,t.kt)("h1",{id:"rgb"},"Rgb"),(0,t.kt)("h2",{id:"rgb-1"},(0,t.kt)("inlineCode",{parentName:"h2"},"Rgb")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"import extras.scala.io.truecolor.Rgb\n")),(0,t.kt)("h2",{id:"rgbfromintint"},(0,t.kt)("inlineCode",{parentName:"h2"},"Rgb.fromInt(Int)")),(0,t.kt)("h3",{id:"rgbfromintint---valid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.fromInt(Int)")," - Valid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.fromInt(0x000000)\n// res1: Either[String, Rgb] = Right(value = Rgb(0, 0, 0))\nRgb.fromInt(0x0000ff)\n// res2: Either[String, Rgb] = Right(value = Rgb(0, 0, 255))\nRgb.fromInt(0x00ff00)\n// res3: Either[String, Rgb] = Right(value = Rgb(0, 255, 0))\nRgb.fromInt(0xff0000)\n// res4: Either[String, Rgb] = Right(value = Rgb(255, 0, 0))\nRgb.fromInt(0xffffff)\n// res5: Either[String, Rgb] = Right(value = Rgb(255, 255, 255))\n")),(0,t.kt)("h3",{id:"rgbfromintint---invalid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.fromInt(Int)")," - Invalid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.fromInt(0x000000 - 1)\n// res6: Either[String, Rgb] = Left(value = "Invalid RGB color. Input: -1")\nRgb.fromInt(0xffffff + 1)\n// res7: Either[String, Rgb] = Left(\n//   value = "Invalid RGB color. Input: 16777216"\n// )\n')),(0,t.kt)("h2",{id:"rgbunsafefromintint"},(0,t.kt)("inlineCode",{parentName:"h2"},"Rgb.unsafeFromInt(Int)")),(0,t.kt)("h3",{id:"rgbunsafefromintint---valid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.unsafeFromInt(Int)")," - Valid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromInt(0x000000)\n// res8: Rgb = Rgb(0, 0, 0)\nRgb.unsafeFromInt(0x0000ff)\n// res9: Rgb = Rgb(0, 0, 255)\nRgb.unsafeFromInt(0x00ff00)\n// res10: Rgb = Rgb(0, 255, 0)\nRgb.unsafeFromInt(0xff0000)\n// res11: Rgb = Rgb(255, 0, 0)\nRgb.unsafeFromInt(0xffffff)\n// res12: Rgb = Rgb(255, 255, 255)\n")),(0,t.kt)("h3",{id:"rgbunsafefromintint---invalid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.unsafeFromInt(Int)")," - Invalid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromInt(0x000000 - 1)\n// java.lang.RuntimeException: Invalid RGB color. Input: -1\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromInt$1(Rgb.scala:46)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromInt(Rgb.scala:46)\n//  at repl.MdocSession$App0$$anonfun$1.apply(rgb.md:63)\n//  at repl.MdocSession$App0$$anonfun$1.apply(rgb.md:63)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromInt(0xffffff + 1)\n// java.lang.RuntimeException: Invalid RGB color. Input: 16777216\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromInt$1(Rgb.scala:46)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromInt(Rgb.scala:46)\n//  at repl.MdocSession$App0$$anonfun$2.apply(rgb.md:73)\n//  at repl.MdocSession$App0$$anonfun$2.apply(rgb.md:73)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.fromRgbInts(0x00, 0x00, 0x00)\n// res13: Either[String, Rgb] = Right(value = Rgb(0, 0, 0))\nRgb.fromRgbInts(0x00, 0x00, 0xff)\n// res14: Either[String, Rgb] = Right(value = Rgb(0, 0, 255))\nRgb.fromRgbInts(0x00, 0xff, 0x00)\n// res15: Either[String, Rgb] = Right(value = Rgb(0, 255, 0))\nRgb.fromRgbInts(0xff, 0x00, 0x00)\n// res16: Either[String, Rgb] = Right(value = Rgb(255, 0, 0))\nRgb.fromRgbInts(0xff, 0xff, 0xff)\n// res17: Either[String, Rgb] = Right(value = Rgb(255, 255, 255))\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00, 0x00, 0x00)\n// res18: Rgb = Rgb(0, 0, 0)\nRgb.unsafeFromRgbInts(0x00, 0x00, 0xff)\n// res19: Rgb = Rgb(0, 0, 255)\nRgb.unsafeFromRgbInts(0x00, 0xff, 0x00)\n// res20: Rgb = Rgb(0, 255, 0)\nRgb.unsafeFromRgbInts(0xff, 0x00, 0x00)\n// res21: Rgb = Rgb(255, 0, 0)\nRgb.unsafeFromRgbInts(0xff, 0xff, 0xff)\n// res22: Rgb = Rgb(255, 255, 255)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00, 0x00, 0x00 - 1)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Blue], Input: (Red: 0, Green: 0, Blue: -1)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$3.apply(rgb.md:119)\n//  at repl.MdocSession$App0$$anonfun$3.apply(rgb.md:119)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00, 0x00, 0xff + 1)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Blue], Input: (Red: 0, Green: 0, Blue: 256)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$4.apply(rgb.md:129)\n//  at repl.MdocSession$App0$$anonfun$4.apply(rgb.md:129)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00, 0x00 - 1, 0x00)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Green], Input: (Red: 0, Green: -1, Blue: 0)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$5.apply(rgb.md:139)\n//  at repl.MdocSession$App0$$anonfun$5.apply(rgb.md:139)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00, 0xff + 1, 0x00)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Green], Input: (Red: 0, Green: 256, Blue: 0)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$6.apply(rgb.md:149)\n//  at repl.MdocSession$App0$$anonfun$6.apply(rgb.md:149)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00 - 1, 0x00, 0x00)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Red], Input: (Red: -1, Green: 0, Blue: 0)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$7.apply(rgb.md:159)\n//  at repl.MdocSession$App0$$anonfun$7.apply(rgb.md:159)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0xff + 1, 0x00, 0x00)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Red], Input: (Red: 256, Green: 0, Blue: 0)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$8.apply(rgb.md:169)\n//  at repl.MdocSession$App0$$anonfun$8.apply(rgb.md:169)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00 - 1, 0x00 - 1, 0x00 - 1)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Red, Green, Blue], Input: (Red: -1, Green: -1, Blue: -1)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$9.apply(rgb.md:179)\n//  at repl.MdocSession$App0$$anonfun$9.apply(rgb.md:179)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00, 0x00 - 1, 0xff + 1)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Green, Blue], Input: (Red: 0, Green: -1, Blue: 256)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$10.apply(rgb.md:189)\n//  at repl.MdocSession$App0$$anonfun$10.apply(rgb.md:189)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0x00 - 1, 0xff + 1, 0x00)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Red, Green], Input: (Red: -1, Green: 256, Blue: 0)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$11.apply(rgb.md:199)\n//  at repl.MdocSession$App0$$anonfun$11.apply(rgb.md:199)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0xff + 1, 0x00, 0x00 - 1)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Red, Blue], Input: (Red: 256, Green: 0, Blue: -1)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$12.apply(rgb.md:209)\n//  at repl.MdocSession$App0$$anonfun$12.apply(rgb.md:209)\n")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromRgbInts(0xff + 1, 0xff + 1, 0xff + 1)\n// java.lang.RuntimeException: Invalid RGB color. Invalid: [Red, Green, Blue], Input: (Red: 256, Green: 256, Blue: 256)\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromRgbInts$1(Rgb.scala:69)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromRgbInts(Rgb.scala:69)\n//  at repl.MdocSession$App0$$anonfun$13.apply(rgb.md:219)\n//  at repl.MdocSession$App0$$anonfun$13.apply(rgb.md:219)\n")),(0,t.kt)("h2",{id:"rgbfromhexstringstring"},(0,t.kt)("inlineCode",{parentName:"h2"},"Rgb.fromHexString(String)")),(0,t.kt)("h3",{id:"rgbfromhexstringstring---valid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.fromHexString(String)")," - Valid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.fromHexString("000000")\n// res23: Either[String, Rgb] = Right(value = Rgb(0, 0, 0))\nRgb.fromHexString("#000000")\n// res24: Either[String, Rgb] = Right(value = Rgb(0, 0, 0))\nRgb.fromHexString("0000ff")\n// res25: Either[String, Rgb] = Right(value = Rgb(0, 0, 255))\nRgb.fromHexString("#0000ff")\n// res26: Either[String, Rgb] = Right(value = Rgb(0, 0, 255))\nRgb.fromHexString("00ff00")\n// res27: Either[String, Rgb] = Right(value = Rgb(0, 255, 0))\nRgb.fromHexString("#00ff00")\n// res28: Either[String, Rgb] = Right(value = Rgb(0, 255, 0))\nRgb.fromHexString("ff0000")\n// res29: Either[String, Rgb] = Right(value = Rgb(255, 0, 0))\nRgb.fromHexString("#ff0000")\n// res30: Either[String, Rgb] = Right(value = Rgb(255, 0, 0))\nRgb.fromHexString("ffffff")\n// res31: Either[String, Rgb] = Right(value = Rgb(255, 255, 255))\nRgb.fromHexString("#ffffff")\n// res32: Either[String, Rgb] = Right(value = Rgb(255, 255, 255))\n')),(0,t.kt)("h3",{id:"rgbfromhexstringstring---invalid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.fromHexString(String)")," - Invalid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.fromHexString("00000000")\n// res33: Either[String, Rgb] = Left(value = "Invalid color hex: 00000000")\nRgb.fromHexString("#00000000")\n// res34: Either[String, Rgb] = Left(value = "Invalid color hex: #00000000")\nRgb.fromHexString("ffffffff")\n// res35: Either[String, Rgb] = Left(value = "Invalid color hex: ffffffff")\nRgb.fromHexString("#ffffffff")\n// res36: Either[String, Rgb] = Left(value = "Invalid color hex: #ffffffff")\n')),(0,t.kt)("h2",{id:"rgbunsafefromhexstringstring"},(0,t.kt)("inlineCode",{parentName:"h2"},"Rgb.unsafeFromHexString(String)")),(0,t.kt)("h3",{id:"rgbunsafefromhexstringstring---valid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.unsafeFromHexString(String)")," - Valid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromHexString("000000")\n// res37: Rgb = Rgb(0, 0, 0)\nRgb.unsafeFromHexString("#000000")\n// res38: Rgb = Rgb(0, 0, 0)\nRgb.unsafeFromHexString("0000ff")\n// res39: Rgb = Rgb(0, 0, 255)\nRgb.unsafeFromHexString("#0000ff")\n// res40: Rgb = Rgb(0, 0, 255)\nRgb.unsafeFromHexString("00ff00")\n// res41: Rgb = Rgb(0, 255, 0)\nRgb.unsafeFromHexString("#00ff00")\n// res42: Rgb = Rgb(0, 255, 0)\nRgb.unsafeFromHexString("ff0000")\n// res43: Rgb = Rgb(255, 0, 0)\nRgb.unsafeFromHexString("#ff0000")\n// res44: Rgb = Rgb(255, 0, 0)\nRgb.unsafeFromHexString("ffffff")\n// res45: Rgb = Rgb(255, 255, 255)\nRgb.unsafeFromHexString("#ffffff")\n// res46: Rgb = Rgb(255, 255, 255)\n')),(0,t.kt)("h3",{id:"rgbunsafefromhexstringstring---invalid"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.unsafeFromHexString(String)")," - Invalid"),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromHexString("00000000")\n// java.lang.RuntimeException: Invalid color hex: 00000000\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromHexString$1(Rgb.scala:89)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromHexString(Rgb.scala:89)\n//  at repl.MdocSession$App0$$anonfun$14.apply(rgb.md:310)\n//  at repl.MdocSession$App0$$anonfun$14.apply(rgb.md:310)\n')),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromHexString("#00000000")\n// java.lang.RuntimeException: Invalid color hex: #00000000\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromHexString$1(Rgb.scala:89)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromHexString(Rgb.scala:89)\n//  at repl.MdocSession$App0$$anonfun$15.apply(rgb.md:320)\n//  at repl.MdocSession$App0$$anonfun$15.apply(rgb.md:320)\n')),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromHexString("ffffffff")\n// java.lang.RuntimeException: Invalid color hex: ffffffff\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromHexString$1(Rgb.scala:89)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromHexString(Rgb.scala:89)\n//  at repl.MdocSession$App0$$anonfun$16.apply(rgb.md:330)\n//  at repl.MdocSession$App0$$anonfun$16.apply(rgb.md:330)\n')),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromHexString("#ffffffff")\n// java.lang.RuntimeException: Invalid color hex: #ffffffff\n//  at scala.sys.package$.error(package.scala:27)\n//  at extras.scala.io.truecolor.Rgb$.$anonfun$unsafeFromHexString$1(Rgb.scala:89)\n//  at scala.util.Either.fold(Either.scala:190)\n//  at extras.scala.io.truecolor.Rgb$.unsafeFromHexString(Rgb.scala:89)\n//  at repl.MdocSession$App0$$anonfun$17.apply(rgb.md:340)\n//  at repl.MdocSession$App0$$anonfun$17.apply(rgb.md:340)\n')),(0,t.kt)("h2",{id:"to-colored-string-form"},"To Colored String Form"),(0,t.kt)("h3",{id:"rgbtoasciiesc"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.toAsciiEsc")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromInt(0x000000).toAsciiEsc\n// res47: String = "\\u001b[38;2;0;0;0m"\nRgb.unsafeFromInt(0x0000ff).toAsciiEsc\n// res48: String = "\\u001b[38;2;0;0;255m"\nRgb.unsafeFromInt(0x00ff00).toAsciiEsc\n// res49: String = "\\u001b[38;2;0;255;0m"\nRgb.unsafeFromInt(0xff0000).toAsciiEsc\n// res50: String = "\\u001b[38;2;255;0;0m"\nRgb.unsafeFromInt(0xffffff).toAsciiEsc\n// res51: String = "\\u001b[38;2;255;255;255m"\n')),(0,t.kt)("h3",{id:"rgbtohex"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.toHex")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromInt(0x000000).toHex\n// res52: String = "000000"\nRgb.unsafeFromInt(0x0000ff).toHex\n// res53: String = "0000ff"\nRgb.unsafeFromInt(0x00ff00).toHex\n// res54: String = "00ff00"\nRgb.unsafeFromInt(0xff0000).toHex\n// res55: String = "ff0000"\nRgb.unsafeFromInt(0xffffff).toHex\n// res56: String = "ffffff"\n')),(0,t.kt)("h3",{id:"rgbtohexhtml"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.toHexHtml")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'Rgb.unsafeFromInt(0x000000).toHexHtml\n// res57: String = "#000000"\nRgb.unsafeFromInt(0x0000ff).toHexHtml\n// res58: String = "#0000ff"\nRgb.unsafeFromInt(0x00ff00).toHexHtml\n// res59: String = "#00ff00"\nRgb.unsafeFromInt(0xff0000).toHexHtml\n// res60: String = "#ff0000"\nRgb.unsafeFromInt(0xffffff).toHexHtml\n// res61: String = "#ffffff"\n')),(0,t.kt)("h3",{id:"rgbtorgbints"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.toRgbInts")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"Rgb.unsafeFromInt(0x000000).toRgbInts\n// res62: (Int, Int, Int) = (0, 0, 0)\nRgb.unsafeFromInt(0x0000ff).toRgbInts\n// res63: (Int, Int, Int) = (0, 0, 255)\nRgb.unsafeFromInt(0x00ff00).toRgbInts\n// res64: (Int, Int, Int) = (0, 255, 0)\nRgb.unsafeFromInt(0xff0000).toRgbInts\n// res65: (Int, Int, Int) = (255, 0, 0)\nRgb.unsafeFromInt(0xffffff).toRgbInts\n// res66: (Int, Int, Int) = (255, 255, 255)\n")),(0,t.kt)("h3",{id:"rgbcolorstring"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.color(String)")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'println(s"${Rgb.unsafeFromInt(0x000000).color("Hello")} World")\n// \x1b[38;2;0;0;0mHello World\nprintln(s"${Rgb.unsafeFromInt(0x0000ff).color("Hello")} World")\n// \x1b[38;2;0;0;255mHello World\nprintln(s"${Rgb.unsafeFromInt(0x00ff00).color("Hello")} World")\n// \x1b[38;2;0;255;0mHello World\nprintln(s"${Rgb.unsafeFromInt(0xff0000).color("Hello")} World")\n// \x1b[38;2;255;0;0mHello World\nprintln(s"${Rgb.unsafeFromInt(0xffffff).color("Hello")} World")\n// \x1b[38;2;255;255;255mHello World\n')),(0,t.kt)("h3",{id:"rgbcoloredstring"},(0,t.kt)("inlineCode",{parentName:"h3"},"Rgb.colored(String)")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'println(s"${Rgb.unsafeFromInt(0x000000).colored("Hello")} World")\n// \x1b[38;2;0;0;0mHello\x1b[0m World\nprintln(s"${Rgb.unsafeFromInt(0x0000ff).colored("Hello")} World")\n// \x1b[38;2;0;0;255mHello\x1b[0m World\nprintln(s"${Rgb.unsafeFromInt(0x00ff00).colored("Hello")} World")\n// \x1b[38;2;0;255;0mHello\x1b[0m World\nprintln(s"${Rgb.unsafeFromInt(0xff0000).colored("Hello")} World")\n// \x1b[38;2;255;0;0mHello\x1b[0m World\nprintln(s"${Rgb.unsafeFromInt(0xffffff).colored("Hello")} World")\n// \x1b[38;2;255;255;255mHello\x1b[0m World\n')),(0,t.kt)("h2",{id:"syntax"},"syntax"),(0,t.kt)("p",null,"To use ",(0,t.kt)("inlineCode",{parentName:"p"},"Rgb")," syntax, import ",(0,t.kt)("inlineCode",{parentName:"p"},"extras.scala.io.syntax.truecolor.rgb._"),"."),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},"import extras.scala.io.syntax.truecolor.rgb._\n")),(0,t.kt)("h3",{id:"stringrgbint-and-stringrgbedint"},(0,t.kt)("inlineCode",{parentName:"h3"},"String.rgb(Int)")," and ",(0,t.kt)("inlineCode",{parentName:"h3"},"String.rgbed(Int)")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'"Hello".rgb(0x10e0ff)\n// res78: String = "\\u001b[38;2;16;224;255mHello"\n"Hello".rgbed(0x10e0ff)\n// res79: String = "\\u001b[38;2;16;224;255mHello\\u001b[0m"\n')),(0,t.kt)("h3",{id:"invalid-rgb-value-handling"},"Invalid RGB Value Handling"),(0,t.kt)("p",null,"In ",(0,t.kt)("inlineCode",{parentName:"p"},".rgb(Int)")," and ",(0,t.kt)("inlineCode",{parentName:"p"},".rgbed(Int)")," syntax,"),(0,t.kt)("ul",null,(0,t.kt)("li",{parentName:"ul"},"If the given RGB ",(0,t.kt)("inlineCode",{parentName:"li"},"Int")," value is less than ",(0,t.kt)("inlineCode",{parentName:"li"},"0")," (",(0,t.kt)("inlineCode",{parentName:"li"},"0x000000"),"), it uses ",(0,t.kt)("inlineCode",{parentName:"li"},"0")," (",(0,t.kt)("inlineCode",{parentName:"li"},"0x000000"),")."),(0,t.kt)("li",{parentName:"ul"},"If the given RGB ",(0,t.kt)("inlineCode",{parentName:"li"},"Int")," value is greater than ",(0,t.kt)("inlineCode",{parentName:"li"},"16777215")," (",(0,t.kt)("inlineCode",{parentName:"li"},"0xffffff"),"), it uses ",(0,t.kt)("inlineCode",{parentName:"li"},"16777215")," (",(0,t.kt)("inlineCode",{parentName:"li"},"0xffffff"),").")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'"Hello".rgb(0x000000 - 1)\n// res80: String = "\\u001b[38;2;0;0;0mHello"\n"Hello".rgb(0xffffff + 1)\n// res81: String = "\\u001b[38;2;255;255;255mHello"\n\n"Hello".rgbed(0xffffff + 1)\n// res82: String = "\\u001b[38;2;255;255;255mHello\\u001b[0m"\n"Hello".rgbed(0xffffff + 1)\n// res83: String = "\\u001b[38;2;255;255;255mHello\\u001b[0m"\n')),(0,t.kt)("h3",{id:"stringrgbrgb-and-stringrgbedrgb"},(0,t.kt)("inlineCode",{parentName:"h3"},"String.rgb(Rgb)")," and ",(0,t.kt)("inlineCode",{parentName:"h3"},"String.rgbed(Rgb)")),(0,t.kt)("pre",null,(0,t.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.scala.io.truecolor.Rgb\nRgb.fromInt(0x11a02f)\n  .map(rgb => "Hello".rgb(rgb))\n// res84: Either[String, String] = Right(value = "\\u001b[38;2;17;160;47mHello")\nRgb.fromInt(0x11a02f)\n  .map(rgb => "Hello".rgbed(rgb))\n// res85: Either[String, String] = Right(\n//   value = "\\u001b[38;2;17;160;47mHello\\u001b[0m"\n// )\n\nfor {\n  a <- Rgb.fromInt(0x11a02f)\n  b <- Rgb.fromInt(0x00eab0)\n} yield {\n  println("Hello".rgb(a))\n  println("Hello".rgb(b))\n  println("Hello".rgbed(a))\n  println("Hello".rgbed(b))\n}\n// \x1b[38;2;17;160;47mHello\n// \x1b[38;2;0;234;176mHello\n// \x1b[38;2;17;160;47mHello\x1b[0m\n// \x1b[38;2;0;234;176mHello\x1b[0m\n// res86: Either[String, Unit] = Right(value = ())\n')))}c.isMDXComponent=!0}}]);