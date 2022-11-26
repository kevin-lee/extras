"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[1382],{3905:(e,t,n)=>{n.d(t,{Zo:()=>m,kt:()=>u});var r=n(7294);function a(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function i(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function o(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?i(Object(n),!0).forEach((function(t){a(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):i(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function s(e,t){if(null==e)return{};var n,r,a=function(e,t){if(null==e)return{};var n,r,a={},i=Object.keys(e);for(r=0;r<i.length;r++)n=i[r],t.indexOf(n)>=0||(a[n]=e[n]);return a}(e,t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(r=0;r<i.length;r++)n=i[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(a[n]=e[n])}return a}var c=r.createContext({}),l=function(e){var t=r.useContext(c),n=t;return e&&(n="function"==typeof e?e(t):o(o({},t),e)),n},m=function(e){var t=l(e.components);return r.createElement(c.Provider,{value:t},e.children)},p={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},f=r.forwardRef((function(e,t){var n=e.components,a=e.mdxType,i=e.originalType,c=e.parentName,m=s(e,["components","mdxType","originalType","parentName"]),f=l(n),u=a,d=f["".concat(c,".").concat(u)]||f[u]||p[u]||i;return n?r.createElement(d,o(o({ref:t},m),{},{components:n})):r.createElement(d,o({ref:t},m))}));function u(e,t){var n=arguments,a=t&&t.mdxType;if("string"==typeof e||a){var i=n.length,o=new Array(i);o[0]=f;var s={};for(var c in t)hasOwnProperty.call(t,c)&&(s[c]=t[c]);s.originalType=e,s.mdxType="string"==typeof e?e:a,o[1]=s;for(var l=2;l<i;l++)o[l]=n[l];return r.createElement.apply(null,o)}return r.createElement.apply(null,n)}f.displayName="MDXCreateElement"},2991:(e,t,n)=>{n.d(t,{Z:()=>h});var r=n(7294),a=n(6010),i=n(2802),o=n(9960),s=n(3919),c=n(5999);const l="cardContainer_fWXF",m="cardTitle_rnsV",p="cardDescription_PWke";function f(e){let{href:t,children:n}=e;return r.createElement(o.Z,{href:t,className:(0,a.Z)("card padding--lg",l)},n)}function u(e){let{href:t,icon:n,title:i,description:o}=e;return r.createElement(f,{href:t},r.createElement("h2",{className:(0,a.Z)("text--truncate",m),title:i},n," ",i),o&&r.createElement("p",{className:(0,a.Z)("text--truncate",p),title:o},o))}function d(e){let{item:t}=e;const n=(0,i.Wl)(t);return n?r.createElement(u,{href:n,icon:"\ud83d\uddc3\ufe0f",title:t.label,description:(0,c.I)({message:"{count} items",id:"theme.docs.DocCard.categoryDescription",description:"The default description for a category card in the generated index about how many items this category includes"},{count:t.items.length})}):null}function y(e){let{item:t}=e;const n=(0,s.Z)(t.href)?"\ud83d\udcc4\ufe0f":"\ud83d\udd17",a=(0,i.xz)(t.docId??void 0);return r.createElement(u,{href:t.href,icon:n,title:t.label,description:null==a?void 0:a.description})}function x(e){let{item:t}=e;switch(t.type){case"link":return r.createElement(y,{item:t});case"category":return r.createElement(d,{item:t});default:throw new Error(`unknown item type ${JSON.stringify(t)}`)}}function g(e){let{className:t}=e;const n=(0,i.jA)();return r.createElement(h,{items:n.items,className:t})}function h(e){const{items:t,className:n}=e;if(!t)return r.createElement(g,e);const o=(0,i.MN)(t);return r.createElement("section",{className:(0,a.Z)("row",n)},o.map(((e,t)=>r.createElement("article",{key:t,className:"col col--6 margin-bottom--lg"},r.createElement(x,{item:e})))))}},1:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>l,contentTitle:()=>s,default:()=>f,frontMatter:()=>o,metadata:()=>c,toc:()=>m});var r=n(7462),a=(n(7294),n(3905)),i=n(2991);const o={sidebar_position:1,title:"syntax"},s=void 0,c={unversionedId:"extras-refinement/syntax/syntax",id:"extras-refinement/syntax/syntax",title:"syntax",description:"syntax",source:"@site/../generated-docs/docs/extras-refinement/syntax/syntax.md",sourceDirName:"extras-refinement/syntax",slug:"/extras-refinement/syntax/",permalink:"/docs/extras-refinement/syntax/",draft:!1,tags:[],version:"current",sidebarPosition:1,frontMatter:{sidebar_position:1,title:"syntax"},sidebar:"tutorialSidebar",previous:{title:"Getting Started",permalink:"/docs/extras-refinement/"},next:{title:"refinement Syntax",permalink:"/docs/extras-refinement/syntax/refinement"}},l={},m=[{value:"<code>syntax</code>",id:"syntax",level:2}],p={toc:m};function f(e){let{components:t,...n}=e;return(0,a.kt)("wrapper",(0,r.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"syntax"},(0,a.kt)("inlineCode",{parentName:"h2"},"syntax")),(0,a.kt)("p",null,"Additional syntax for ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/estatico/scala-newtype"},"newtype")," and ",(0,a.kt)("a",{parentName:"p",href:"https://github.com/fthomas/refined"},"refined")),(0,a.kt)("ul",null,(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("a",{parentName:"li",href:"/docs/extras-refinement/syntax/refinement"},"refinement")," (for ",(0,a.kt)("inlineCode",{parentName:"li"},"newtype")," and ",(0,a.kt)("inlineCode",{parentName:"li"},"refined"),")"),(0,a.kt)("li",{parentName:"ul"},(0,a.kt)("a",{parentName:"li",href:"/docs/extras-refinement/syntax/string"},"string")," (for ",(0,a.kt)("inlineCode",{parentName:"li"},"String")," from ",(0,a.kt)("inlineCode",{parentName:"li"},"refined"),")")),(0,a.kt)("p",null,"If you want to import all syntaxes, use"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"import extras.refinement.syntax.all._\n")),(0,a.kt)("p",null,"which is equivalent to"),(0,a.kt)("pre",null,(0,a.kt)("code",{parentName:"pre",className:"language-scala"},"import extras.refinement.syntax.refinement._\nimport extras.refinement.syntax.string._\n")),(0,a.kt)(i.Z,{mdxType:"DocCardList"}))}f.isMDXComponent=!0}}]);