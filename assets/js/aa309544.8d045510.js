"use strict";(self.webpackChunkwebsite=self.webpackChunkwebsite||[]).push([[841],{3905:(e,t,n)=>{n.d(t,{Zo:()=>m,kt:()=>u});var i=n(7294);function l(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function r(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);t&&(i=i.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,i)}return n}function a(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?r(Object(n),!0).forEach((function(t){l(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):r(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function o(e,t){if(null==e)return{};var n,i,l=function(e,t){if(null==e)return{};var n,i,l={},r=Object.keys(e);for(i=0;i<r.length;i++)n=r[i],t.indexOf(n)>=0||(l[n]=e[n]);return l}(e,t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(i=0;i<r.length;i++)n=r[i],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(l[n]=e[n])}return l}var s=i.createContext({}),p=function(e){var t=i.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):a(a({},t),e)),n},m=function(e){var t=p(e.components);return i.createElement(s.Provider,{value:t},e.children)},d={inlineCode:"code",wrapper:function(e){var t=e.children;return i.createElement(i.Fragment,{},t)}},c=i.forwardRef((function(e,t){var n=e.components,l=e.mdxType,r=e.originalType,s=e.parentName,m=o(e,["components","mdxType","originalType","parentName"]),c=p(n),u=l,f=c["".concat(s,".").concat(u)]||c[u]||d[u]||r;return n?i.createElement(f,a(a({ref:t},m),{},{components:n})):i.createElement(f,a({ref:t},m))}));function u(e,t){var n=arguments,l=t&&t.mdxType;if("string"==typeof e||l){var r=n.length,a=new Array(r);a[0]=c;var o={};for(var s in t)hasOwnProperty.call(t,s)&&(o[s]=t[s]);o.originalType=e,o.mdxType="string"==typeof e?e:l,a[1]=o;for(var p=2;p<r;p++)a[p]=n[p];return i.createElement.apply(null,a)}return i.createElement.apply(null,n)}c.displayName="MDXCreateElement"},6715:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>s,contentTitle:()=>a,default:()=>d,frontMatter:()=>r,metadata:()=>o,toc:()=>p});var i=n(7462),l=(n(7294),n(3905));const r={sidebar_position:4,id:"file",title:"File"},a="File",o={unversionedId:"extras-scala-io/file",id:"extras-scala-io/file",title:"File",description:"syntax",source:"@site/../generated-docs/docs/extras-scala-io/file.md",sourceDirName:"extras-scala-io",slug:"/extras-scala-io/file",permalink:"/docs/extras-scala-io/file",draft:!1,tags:[],version:"current",sidebarPosition:4,frontMatter:{sidebar_position:4,id:"file",title:"File"},sidebar:"tutorialSidebar",previous:{title:"Rgb",permalink:"/docs/extras-scala-io/truecolor/rgb"},next:{title:"Getting Started",permalink:"/docs/extras-concurrent/"}},s={},p=[{value:"syntax",id:"syntax",level:2},{value:"<code>listAllFilesRecursively</code>",id:"listallfilesrecursively",level:3},{value:"<code>deleteAllRecursively</code>",id:"deleteallrecursively",level:3},{value:"<code>TempFiles</code>",id:"tempfiles",level:2},{value:"<code>runWithTempDir</code>",id:"runwithtempdir",level:3},{value:"Example: test with hedgehog",id:"example-test-with-hedgehog",level:4}],m={toc:p};function d(e){let{components:t,...n}=e;return(0,l.kt)("wrapper",(0,i.Z)({},m,n,{components:t,mdxType:"MDXLayout"}),(0,l.kt)("h1",{id:"file"},"File"),(0,l.kt)("h2",{id:"syntax"},"syntax"),(0,l.kt)("h3",{id:"listallfilesrecursively"},(0,l.kt)("inlineCode",{parentName:"h3"},"listAllFilesRecursively")),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"listAllFilesRecursively")," in ",(0,l.kt)("inlineCode",{parentName:"p"},"import extras.scala.io.file.syntax")),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"listAllFilesRecursively")," returns all files and directories in the given ",(0,l.kt)("inlineCode",{parentName:"li"},"File")," as well as the given file itself if it is a directory."),(0,l.kt)("li",{parentName:"ul"},"If it's a file, it returns a ",(0,l.kt)("inlineCode",{parentName:"li"},"List")," containing the given ",(0,l.kt)("inlineCode",{parentName:"li"},"File"),".")),(0,l.kt)("admonition",{type:"note"},(0,l.kt)("p",{parentName:"admonition"},"The result of ",(0,l.kt)("inlineCode",{parentName:"p"},"listAllFilesRecursively")," is not sorted, so you need to sort it yourself.",(0,l.kt)("br",null),"\ne.g.)"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'listAllFilesRecursively("path/to/file").sorted\n'))),(0,l.kt)("p",null,"e.g.) If the file structure looks like this"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre"},"\uf115 /tmp/a\n\u251c\u2500\u2500 \uf115 b\n\u2502  \u251c\u2500\u2500 \uf115 b-1\n\u2502  \u2502  \u251c\u2500\u2500 \uf15c b-1-1.txt\n\u2502  \u2502  \u251c\u2500\u2500 \uf15c b-1-2.txt\n\u2502  \u2502  \u2514\u2500\u2500 \uf15c b-1-3.txt\n\u2502  \u2514\u2500\u2500 \uf115 b-2\n\u2502     \u251c\u2500\u2500 \uf115 b-2-1\n\u2502     \u2502  \u251c\u2500\u2500 \uf15c b-2-1-1.txt\n\u2502     \u2502  \u2514\u2500\u2500 \uf15c b-2-1-2.txt\n\u2502     \u2514\u2500\u2500 \uf115 b-2-2\n\u251c\u2500\u2500 \uf115 c\n\u2502  \u2514\u2500\u2500 \uf15c c-1.txt\n\u2514\u2500\u2500 \uf115 d\n   \u251c\u2500\u2500 \uf115 d-1\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-1.txt\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-2.txt\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-3.txt\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-4.txt\n   \u2502  \u2514\u2500\u2500 \uf15c d-1-5.txt\n   \u251c\u2500\u2500 \uf15c d-2.txt\n   \u2514\u2500\u2500 \uf15c d-3.txt\n")),(0,l.kt)("p",null,"the result of ",(0,l.kt)("inlineCode",{parentName:"p"},"listAllFilesRecursively")," looks like this."),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.scala.io.file.syntax._\nimport java.io.File\n\nlistAllFilesRecursively(new File("/tmp/a")).sorted\n\n// List(\n//   /tmp/a,\n//   /tmp/a/b,\n//   /tmp/a/b/b-1,\n//   /tmp/a/b/b-1/b-1-1.txt,\n//   /tmp/a/b/b-1/b-1-2.txt,\n//   /tmp/a/b/b-1/b-1-3.txt,\n//   /tmp/a/b/b-2,\n//   /tmp/a/b/b-2/b-2-1,\n//   /tmp/a/b/b-2/b-2-1/b-2-1-1.txt,\n//   /tmp/a/b/b-2/b-2-1/b-2-1-2.txt,\n//   /tmp/a/b/b-2/b-2-2,\n//   /tmp/a/c,\n//   /tmp/a/c/c-1.txt,\n//   /tmp/a/d,\n//   /tmp/a/d/d-1,\n//   /tmp/a/d/d-1/d-1-1.txt,\n//   /tmp/a/d/d-1/d-1-2.txt,\n//   /tmp/a/d/d-1/d-1-3.txt,\n//   /tmp/a/d/d-1/d-1-4.txt,\n//   /tmp/a/d/d-1/d-1-5.txt,\n//   /tmp/a/d/d-2.txt,\n//   /tmp/a/d/d-3.txt\n// )\n')),(0,l.kt)("p",null,"or"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'new File("/tmp/a").listAllFilesRecursively.sorted\n')),(0,l.kt)("h3",{id:"deleteallrecursively"},(0,l.kt)("inlineCode",{parentName:"h3"},"deleteAllRecursively")),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"deleteAllRecursively")," in ",(0,l.kt)("inlineCode",{parentName:"p"},"import extras.scala.io.file.syntax")," removes the given file and all the files and directories in it if the given file is a directory."),(0,l.kt)("p",null,"e.g.) If the file structure looks like this"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre"},"\uf115 /tmp/a\n\u251c\u2500\u2500 \uf115 b\n\u2502  \u251c\u2500\u2500 \uf115 b-1\n\u2502  \u2502  \u251c\u2500\u2500 \uf15c b-1-1.txt\n\u2502  \u2502  \u251c\u2500\u2500 \uf15c b-1-2.txt\n\u2502  \u2502  \u2514\u2500\u2500 \uf15c b-1-3.txt\n\u2502  \u2514\u2500\u2500 \uf115 b-2\n\u2502     \u251c\u2500\u2500 \uf115 b-2-1\n\u2502     \u2502  \u251c\u2500\u2500 \uf15c b-2-1-1.txt\n\u2502     \u2502  \u2514\u2500\u2500 \uf15c b-2-1-2.txt\n\u2502     \u2514\u2500\u2500 \uf115 b-2-2\n\u251c\u2500\u2500 \uf115 c\n\u2502  \u2514\u2500\u2500 \uf15c c-1.txt\n\u2514\u2500\u2500 \uf115 d\n   \u251c\u2500\u2500 \uf115 d-1\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-1.txt\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-2.txt\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-3.txt\n   \u2502  \u251c\u2500\u2500 \uf15c d-1-4.txt\n   \u2502  \u2514\u2500\u2500 \uf15c d-1-5.txt\n   \u251c\u2500\u2500 \uf15c d-2.txt\n   \u2514\u2500\u2500 \uf15c d-3.txt\n")),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"deleteAllRecursively")," deletes the given file and everything in it."),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.scala.io.file.syntax._\nimport java.io.File\n\ndeleteAllRecursively(new File("/tmp/a"))\n// The `/tmp/a` and everything inside is removed.\n')),(0,l.kt)("p",null,"or"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'new File("/tmp/a").deleteAllRecursively()\n')),(0,l.kt)("h2",{id:"tempfiles"},(0,l.kt)("inlineCode",{parentName:"h2"},"TempFiles")),(0,l.kt)("h3",{id:"runwithtempdir"},(0,l.kt)("inlineCode",{parentName:"h3"},"runWithTempDir")),(0,l.kt)("p",null,"If you want to do something any temporary folder which should be deleted once it's done, you can use ",(0,l.kt)("inlineCode",{parentName:"p"},"extras.scala.io.file.TempFiles.runWithTempDir"),"."),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'import extras.scala.io.file.TempFiles\nimport java.io.File\n\ndef foo(file: File): Unit =\n  if (file.exists)\n    println(s"${file.getParentFile.getName}/${file.getName} exists")\n  else\n    println(s"${file.getParentFile.getName}/${file.getName} does not exist.")\n\nvar tmp: Option[File] = None\n// tmp: Option[File] = None\n\nTempFiles.runWithTempDir("temporary-dir-prefix") { tempDir =>\n\n  tmp = Some(tempDir.value) // To check if the directory exists outside this block.\n  \n  tmp.foreach(foo)\n\n  val tmpDir = tempDir.value\n  val someFile = new File(tmpDir, "myfile.txt")\n  someFile.createNewFile()\n  foo(someFile)\n  \n  val someFile2 = new File(tmpDir, "myfile2.txt")\n  foo(someFile2)\n  \n  "Done"\n}\n// tmp/temporary-dir-prefix17953094252656588801 exists\n// temporary-dir-prefix17953094252656588801/myfile.txt exists\n// temporary-dir-prefix17953094252656588801/myfile2.txt does not exist.\n// res1: Either[Throwable, String] = Right(value = "Done")\n\ntmp.foreach{ file => \n  println(s"${file.getParentFile.getName}/${file.getName}")\n  foo(file)\n}\n// tmp/temporary-dir-prefix17953094252656588801\n// tmp/temporary-dir-prefix17953094252656588801 does not exist.\n')),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"tempDir")," is of type ",(0,l.kt)("inlineCode",{parentName:"p"},"TempDir")," which is just this value class."),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},"final case class TempDir(value: java.io.File) extends AnyVal\n")),(0,l.kt)("p",null,(0,l.kt)("inlineCode",{parentName:"p"},"TempFiles.runWithTempDir")," is useful when you test with files and need to remove them once the test is done."),(0,l.kt)("h4",{id:"example-test-with-hedgehog"},"Example: test with hedgehog"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-scala"},'import hedgehog._\nimport hedgehog.runner._\n\nimport extras.scala.io.file.TempFiles\nimport java.io._\nimport scala.util.Using\n\nobject MyTest extends Properties {\n  def tests: List[Test] = List(\n    property("test something with files", testSomethingWithFiles)\n  )\n  \n  def testSomethingWithFiles: Property = for {\n    filename <- Gen.string(Gen.alphaNum, Range.linear(3, 5)).log("filename")\n    content1 <- Gen.string(Gen.alphaNum, Range.linear(10, 100)).log("content1")\n    content2 <- Gen.string(Gen.alphaNum, Range.linear(10, 100)).log("content2")\n  } yield {\n    val content = s"$content1\\n$content2"\n    TempFiles.runWithTempDir("my-temp-dir") { tempDir =>\n      val rootDir = tempDir.value\n      val file = new File(rootDir, filename)\n      \n      (for { \n        _      <- Using(new PrintWriter(file))(_.write(content))\n        result <- Using(scala.io.Source.fromFile(file))(_.mkString)\n      } yield result).toEither\n      \n    }.joinRight match {\n      case Right(actual) => actual ==== content\n      case Left(err) => Result.failure.log(s"Failed: ${err.getMessage}")\n    }\n  }\n  \n}\n')))}d.isMDXComponent=!0}}]);