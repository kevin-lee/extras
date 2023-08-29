"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[8631],{3905:(e,t,a)=>{a.d(t,{Zo:()=>c,kt:()=>d});var r=a(7294);function n(e,t,a){return t in e?Object.defineProperty(e,t,{value:a,enumerable:!0,configurable:!0,writable:!0}):e[t]=a,e}function l(e,t){var a=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),a.push.apply(a,r)}return a}function s(e){for(var t=1;t<arguments.length;t++){var a=null!=arguments[t]?arguments[t]:{};t%2?l(Object(a),!0).forEach((function(t){n(e,t,a[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(a)):l(Object(a)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(a,t))}))}return e}function o(e,t){if(null==e)return{};var a,r,n=function(e,t){if(null==e)return{};var a,r,n={},l=Object.keys(e);for(r=0;r<l.length;r++)a=l[r],t.indexOf(a)>=0||(n[a]=e[a]);return n}(e,t);if(Object.getOwnPropertySymbols){var l=Object.getOwnPropertySymbols(e);for(r=0;r<l.length;r++)a=l[r],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(n[a]=e[a])}return n}var i=r.createContext({}),p=function(e){var t=r.useContext(i),a=t;return e&&(a="function"==typeof e?e(t):s(s({},t),e)),a},c=function(e){var t=p(e.components);return r.createElement(i.Provider,{value:t},e.children)},k="mdxType",g={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},m=r.forwardRef((function(e,t){var a=e.components,n=e.mdxType,l=e.originalType,i=e.parentName,c=o(e,["components","mdxType","originalType","parentName"]),k=p(a),m=n,d=k["".concat(i,".").concat(m)]||k[m]||g[m]||l;return a?r.createElement(d,s(s({ref:t},c),{},{components:a})):r.createElement(d,s({ref:t},c))}));function d(e,t){var a=arguments,n=t&&t.mdxType;if("string"==typeof e||n){var l=a.length,s=new Array(l);s[0]=m;var o={};for(var i in t)hasOwnProperty.call(t,i)&&(o[i]=t[i]);o.originalType=e,o[k]="string"==typeof e?e:n,s[1]=o;for(var p=2;p<l;p++)s[p]=a[p];return r.createElement.apply(null,s)}return r.createElement.apply(null,a)}m.displayName="MDXCreateElement"},2329:(e,t,a)=>{a.r(t),a.d(t,{assets:()=>i,contentTitle:()=>s,default:()=>g,frontMatter:()=>l,metadata:()=>o,toc:()=>p});var r=a(7462),n=(a(7294),a(3905));const l={sidebar_position:1,id:"intro",title:"Extras",slug:"/"},s=void 0,o={unversionedId:"intro",id:"intro",title:"Extras",description:"extras",source:"@site/../generated-docs/docs/intro.md",sourceDirName:".",slug:"/",permalink:"/docs/",draft:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,id:"intro",title:"Extras",slug:"/"},sidebar:"tutorialSidebar",next:{title:"Getting Started",permalink:"/docs/extras-string/"}},i={},p=[{value:"extras",id:"extras",level:2},{value:"extras-render",id:"extras-render",level:2},{value:"extras-string",id:"extras-string",level:2},{value:"extras-cats",id:"extras-cats",level:2},{value:"extras-circe",id:"extras-circe",level:2},{value:"extras-scala-io",id:"extras-scala-io",level:2},{value:"extras-hedgehog-ce3",id:"extras-hedgehog-ce3",level:2},{value:"extras-hedgehog-circe",id:"extras-hedgehog-circe",level:2},{value:"extras-refinement",id:"extras-refinement",level:2},{value:"extras-type-info",id:"extras-type-info",level:2},{value:"extras-fs2",id:"extras-fs2",level:2},{value:"extras-testing-tools",id:"extras-testing-tools",level:2},{value:"extras-doobie-tools",id:"extras-doobie-tools",level:2},{value:"extras-concurrent",id:"extras-concurrent",level:2},{value:"<del>extras-reflects</del>",id:"extras-reflects",level:2}],c={toc:p},k="wrapper";function g(e){let{components:t,...a}=e;return(0,n.kt)(k,(0,r.Z)({},c,a,{components:t,mdxType:"MDXLayout"}),(0,n.kt)("h2",{id:"extras"},"extras"),(0,n.kt)("p",null,(0,n.kt)("a",{parentName:"p",href:"https://github.com/kevin-lee/extras/actions?workflow=Build-All"},(0,n.kt)("img",{parentName:"a",src:"https://github.com/kevin-lee/extras/workflows/Build-All/badge.svg",alt:"Build Status"})),"\n",(0,n.kt)("a",{parentName:"p",href:"https://github.com/kevin-lee/extras/actions?workflow=Release"},(0,n.kt)("img",{parentName:"a",src:"https://github.com/kevin-lee/extras/workflows/Release/badge.svg",alt:"Release Status"})),"\n",(0,n.kt)("a",{parentName:"p",href:"https://index.scala-lang.org/kevin-lee/extras"},(0,n.kt)("img",{parentName:"a",src:"https://index.scala-lang.org/kevin-lee/extras/latest.svg",alt:"Latest version"}))),(0,n.kt)("table",null,(0,n.kt)("thead",{parentName:"table"},(0,n.kt)("tr",{parentName:"thead"},(0,n.kt)("th",{parentName:"tr",align:"right"},"Project"),(0,n.kt)("th",{parentName:"tr",align:null},"Maven Central"))),(0,n.kt)("tbody",{parentName:"table"},(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-render"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-render_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-render_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-cats"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-cats_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-cats_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-circe"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-circe_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-circe_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-scala-io"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-scala-io_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-scala-io_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-hedgehog-ce3"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-hedgehog-ce3_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-hedgehog-ce3_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-hedgehog-circe"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-hedgehog-circe_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-hedgehog-circe_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-type-info"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-type-info_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-type-info_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-testing-tools"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-testing-tools_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-testing-tools_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-fs2-v*-text"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-fs2-v3-text_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-fs2-v3-text_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-refinement"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-refinement_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-refinement_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-concurrent"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-concurrent_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-concurrent_2.13/badge.svg",alt:"Maven Central"})))),(0,n.kt)("tr",{parentName:"tbody"},(0,n.kt)("td",{parentName:"tr",align:"right"},"extras-concurrent-testing"),(0,n.kt)("td",{parentName:"tr",align:null},(0,n.kt)("a",{parentName:"td",href:"https://search.maven.org/artifact/io.kevinlee/extras-concurrent-testing_2.13"},(0,n.kt)("img",{parentName:"a",src:"https://maven-badges.herokuapp.com/maven-central/io.kevinlee/extras-concurrent-testing_2.13/badge.svg",alt:"Maven Central"})))))),(0,n.kt)("admonition",{type:"info"},(0,n.kt)("p",{parentName:"admonition"},"Supported Scala Versions: ",(0,n.kt)("inlineCode",{parentName:"p"},"3"),", ",(0,n.kt)("inlineCode",{parentName:"p"},"2.13")," and ",(0,n.kt)("inlineCode",{parentName:"p"},"2.12")),(0,n.kt)("p",{parentName:"admonition"},"Show ",(0,n.kt)("a",{parentName:"p",href:"https://index.scala-lang.org/kevin-lee/extras/artifacts"},(0,n.kt)("strong",{parentName:"a"},"all ",(0,n.kt)("inlineCode",{parentName:"strong"},"extras")," versions\n")))),(0,n.kt)("h2",{id:"extras-render"},"extras-render"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-render"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-render")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-render" % "0.41.0"\n')),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-render-refined" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-string"},"extras-string"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-string"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-string")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-string" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-cats"},"extras-cats"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-cats"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-cats")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-cats" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-circe"},"extras-circe"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-circe"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-circe")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-circe" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-scala-io"},"extras-scala-io"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-scala-io"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-scala-io")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-scala-io" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-hedgehog-ce3"},"extras-hedgehog-ce3"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-hedgehog/extras-hedgehog-ce3"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-hedgehog-ce3")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-hedgehog-ce3" % "0.41.0" % Test\n')),(0,n.kt)("h2",{id:"extras-hedgehog-circe"},"extras-hedgehog-circe"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-hedgehog/extras-hedgehog-circe"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-hedgehog-circe")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-hedgehog-circe" % "0.41.0" % Test\n')),(0,n.kt)("h2",{id:"extras-refinement"},"extras-refinement"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-refinement"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-refinement")))," (n/a to Scala 3)"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-refinement" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-type-info"},"extras-type-info"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-type-info"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-type-info")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-type-info" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-fs2"},"extras-fs2"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-fs2"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-fs2")))),(0,n.kt)("p",null,"For Fs2 v2"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-fs2-v2-text" % "0.41.0"\n')),(0,n.kt)("p",null,"For Fs2 v3"),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-fs2-v3-text" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-testing-tools"},"extras-testing-tools"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-testing-tools"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-testing-tools")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-testing-tools" % "0.41.0"\n')),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-testing-tools-cats" % "0.41.0"\n')),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-testing-tools-effectie" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-doobie-tools"},"extras-doobie-tools"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-doobie-tools"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-doobie-tools")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-doobie-tools-ce2" % "0.41.0"\n')),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-doobie-tools-ce3" % "0.41.0"\n')),(0,n.kt)("h2",{id:"extras-concurrent"},"extras-concurrent"),(0,n.kt)("p",null,"Go to ",(0,n.kt)("a",{parentName:"p",href:"extras-concurrent"},(0,n.kt)("u",null,(0,n.kt)("strong",{parentName:"a"},"extras-concurrent")))),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-concurrent" % "0.41.0"\n')),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-concurrent-testing" % "0.41.0" % Test\n')),(0,n.kt)("h2",{id:"extras-reflects"},(0,n.kt)("del",{parentName:"h2"},"extras-reflects")),(0,n.kt)("p",null,(0,n.kt)("del",{parentName:"p"},"extras-reflects (n/a to Scala 3)")," - ",(0,n.kt)("strong",{parentName:"p"},"Deprecated")),(0,n.kt)("pre",null,(0,n.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-reflects" % "0.41.0"\n')))}g.isMDXComponent=!0}}]);