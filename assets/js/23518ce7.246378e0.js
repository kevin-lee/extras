"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[681],{3905:(e,t,r)=>{r.d(t,{Zo:()=>m,kt:()=>u});var n=r(7294);function a(e,t,r){return t in e?Object.defineProperty(e,t,{value:r,enumerable:!0,configurable:!0,writable:!0}):e[t]=r,e}function i(e,t){var r=Object.keys(e);if(Object.getOwnPropertySymbols){var n=Object.getOwnPropertySymbols(e);t&&(n=n.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),r.push.apply(r,n)}return r}function o(e){for(var t=1;t<arguments.length;t++){var r=null!=arguments[t]?arguments[t]:{};t%2?i(Object(r),!0).forEach((function(t){a(e,t,r[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(r)):i(Object(r)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(r,t))}))}return e}function c(e,t){if(null==e)return{};var r,n,a=function(e,t){if(null==e)return{};var r,n,a={},i=Object.keys(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||(a[r]=e[r]);return a}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(n=0;n<i.length;n++)r=i[n],t.indexOf(r)>=0||Object.prototype.propertyIsEnumerable.call(e,r)&&(a[r]=e[r])}return a}var s=n.createContext({}),l=function(e){var t=n.useContext(s),r=t;return e&&(r="function"==typeof e?e(t):o(o({},t),e)),r},m=function(e){var t=l(e.components);return n.createElement(s.Provider,{value:t},e.children)},f={inlineCode:"code",wrapper:function(e){var t=e.children;return n.createElement(n.Fragment,{},t)}},p=n.forwardRef((function(e,t){var r=e.components,a=e.mdxType,i=e.originalType,s=e.parentName,m=c(e,["components","mdxType","originalType","parentName"]),p=l(r),u=a,d=p["".concat(s,".").concat(u)]||p[u]||f[u]||i;return r?n.createElement(d,o(o({ref:t},m),{},{components:r})):n.createElement(d,o({ref:t},m))}));function u(e,t){var r=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=r.length,o=new Array(i);o[0]=p;var c={};for(var s in t)hasOwnProperty.call(t,s)&&(c[s]=t[s]);c.originalType=e,c.mdxType="string"==typeof e?e:a,o[1]=c;for(var l=2;l<i;l++)o[l]=r[l];return n.createElement.apply(null,o)}return n.createElement.apply(null,r)}p.displayName="MDXCreateElement"},2991:(e,t,r)=>{r.d(t,{Z:()=>h});var n=r(7294),a=r(6010),i=r(2802),o=r(9960),c=r(3919),s=r(5999);const l="cardContainer_fWXF",m="cardTitle_rnsV",f="cardDescription_PWke";function p(e){let{href:t,children:r}=e;return n.createElement(o.Z,{href:t,className:(0,a.Z)("card padding--lg",l)},r)}function u(e){let{href:t,icon:r,title:i,description:o}=e;return n.createElement(p,{href:t},n.createElement("h2",{className:(0,a.Z)("text--truncate",m),title:i},r," ",i),o&&n.createElement("p",{className:(0,a.Z)("text--truncate",f),title:o},o))}function d(e){let{item:t}=e;const r=(0,i.Wl)(t);return r?n.createElement(u,{href:r,icon:"\ud83d\uddc3\ufe0f",title:t.label,description:(0,s.I)({message:"{count} items",id:"theme.docs.DocCard.categoryDescription",description:"The default description for a category card in the generated index about how many items this category includes"},{count:t.items.length})}):null}function y(e){let{item:t}=e;const r=(0,c.Z)(t.href)?"\ud83d\udcc4\ufe0f":"\ud83d\udd17",a=(0,i.xz)(t.docId??void 0);return n.createElement(u,{href:t.href,icon:r,title:t.label,description:null==a?void 0:a.description})}function g(e){let{item:t}=e;switch(t.type){case"link":return n.createElement(y,{item:t});case"category":return n.createElement(d,{item:t});default:throw new Error(`unknown item type ${JSON.stringify(t)}`)}}function b(e){let{className:t}=e;const r=(0,i.jA)();return n.createElement(h,{items:r.items,className:t})}function h(e){const{items:t,className:r}=e;if(!t)return n.createElement(b,e);const o=(0,i.MN)(t);return n.createElement("section",{className:(0,a.Z)("row",r)},o.map(((e,t)=>n.createElement("article",{key:t,className:"col col--6 margin-bottom--lg"},n.createElement(g,{item:e})))))}},2478:(e,t,r)=>{r.r(t),r.d(t,{assets:()=>l,contentTitle:()=>c,default:()=>p,frontMatter:()=>o,metadata:()=>s,toc:()=>m});var n=r(7462),a=(r(7294),r(3905)),i=r(2991);const o={sidebar_position:1,title:"Getting Started"},c=void 0,s={unversionedId:"extras-refinement/extras-refinement",id:"extras-refinement/extras-refinement",title:"Getting Started",description:"Get extras-refinement",source:"@site/../generated-docs/target/mdoc/extras-refinement/extras-refinement.md",sourceDirName:"extras-refinement",slug:"/extras-refinement/",permalink:"/docs/extras-refinement/",draft:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,title:"Getting Started"},sidebar:"tutorialSidebar",previous:{title:"Reflects Syntax",permalink:"/docs/extras-reflects/syntax"},next:{title:"syntax",permalink:"/docs/extras-refinement/syntax/"}},l={},m=[{value:"Get <code>extras-refinement</code>",id:"get-extras-refinement",level:2}],f={toc:m};function p(e){let{components:t,...r}=e;return(0,a.kt)("wrapper",(0,n.Z)({},f,r,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"get-extras-refinement"},"Get ",(0,a.kt)("inlineCode",{parentName:"h2"},"extras-refinement")),(0,a.kt)("p",null,(0,a.kt)("inlineCode",{parentName:"p"},"extras-refinement")," provides syntax to use ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/estatico/scala-newtype"},"newtype")," and ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/fthomas/refined"},"refined"),"."),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},'"io.kevinlee" %% "extras-refinement" % "0.20.0"\n')),(0,a.kt)(i.Z,{mdxType:"DocCardList"}))}p.isMDXComponent=!0}}]);