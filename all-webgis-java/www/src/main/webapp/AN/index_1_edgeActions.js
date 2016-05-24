/***********************
* Adobe Edge Animate 组件动作
*
* 小心编辑这个文件，小心保存
* 函数签名和注释以“Edge”起始来维持
* 通过 Adobe Edge Animate 使动作可以交互
*
***********************/
(function($, Edge, compId){
var Composition = Edge.Composition, Symbol = Edge.Symbol; // 常用的 Edge 类别名

   //Edge symbol: 'stage'
   (function(symbolName) {
      
      
      Symbol.bindElementAction(compId, symbolName, "${float_cloud3}", "click", function(sym, e) {
         // 插入代码——当鼠标在此处单击
         EC.info("dddd");

      });
      //Edge binding end

      Symbol.bindSymbolAction(compId, symbolName, "beforeDeletion", function(sym, e) {
         // 当符号被删除时插入的代码将运行
         
      });
      //Edge binding end

      Symbol.bindSymbolAction(compId, symbolName, "creationComplete", function(sym, e) {
         // 当符号在此处创建时插入的代码将运行
         $("body").on("page_in",function(e){
         	sym.play(0);
         })
         
         $("body").on("page_leave",function(e){
         	sym.stop();
         
         })
         

      });
      //Edge binding end

   })("stage");
   //Edge symbol end:'stage'

   //=========================================================
   
   //Edge symbol: 'float_cloud'
   (function(symbolName) {   
   
      

      

      Symbol.bindSymbolAction(compId, symbolName, "creationComplete", function(sym, e) {
         // 当符号在此处创建时插入的代码将运行
         
         var co = window["con"+"sole"];
         //co.log(sym);
         
         sym.play((Math.random() * sym.getDuration())>>0)
         
         
         

      });
      //Edge binding end

      Symbol.bindTimelineAction(compId, symbolName, "Default Timeline", "complete", function(sym, e) {
         // 当时间轴在此处结束时插入的代码将运行
         	sym.play(0);

      });
      //Edge binding end

   })("float_cloud");
   //Edge symbol end:'float_cloud'

   //=========================================================
   
   //Edge symbol: 'rocket'
   (function(symbolName) {   
   
      Symbol.bindTimelineAction(compId, symbolName, "Default Timeline", "complete", function(sym, e) {
         sym.play(0);// 当时间轴在此处结束时插入的代码将运行

      });
      //Edge binding end

   })("rocket");
   //Edge symbol end:'rocket'

   //=========================================================
   
   //Edge symbol: 'line_fame_1'
   (function(symbolName) {   
   
      Symbol.bindTimelineAction(compId, symbolName, "Default Timeline", "complete", function(sym, e) {
         // 当时间轴在此处结束时插入的代码将运行
         sym.play(0);
         

      });
      //Edge binding end

   })("line_fame_1");
   //Edge symbol end:'line_fame_1'

   //=========================================================
   
   //Edge symbol: 'q'
   (function(symbolName) {   
   
      Symbol.bindTimelineAction(compId, symbolName, "Default Timeline", "complete", function(sym, e) {
         // 当时间轴在此处结束时插入的代码将运行
         // 从标签或特定时间开始播放时间轴。例如：
         // sym.play(500); 或 sym.play("myLabel");
         sym.play(0);
         

      });
      //Edge binding end

   })("q");
   //Edge symbol end:'q'

   //=========================================================
   
   //Edge symbol: 'line_frame_big'
   (function(symbolName) {   
   
      Symbol.bindTimelineAction(compId, symbolName, "Default Timeline", "complete", function(sym, e) {
         // 当时间轴在此处结束时插入的代码将运行
         // 从头开始重放音频轨，无视当前的播放状态
         if (!sym.isPlaying() ) {
         	sym.play(0);
         }
         

      });
      //Edge binding end

   })("line_frame_big");
   //Edge symbol end:'line_frame_big'

   //=========================================================
   
   //Edge symbol: 'hotball'
   (function(symbolName) {   
   
      Symbol.bindTimelineAction(compId, symbolName, "Default Timeline", "complete", function(sym, e) {
         // 当时间轴在此处结束时插入的代码将运行
         // 从头开始重放音频轨，无视当前的播放状态
         if (!sym.isPlaying() ) {
         	sym.play(0);
         }
         

      });
      //Edge binding end

   })("hotball");
   //Edge symbol end:'hotball'

   //=========================================================
   
   //Edge symbol: '预载'
   (function(symbolName) {   
   
   })("预载");
   //Edge symbol end:'预载'

})(window.jQuery || AdobeEdge.$, AdobeEdge, "IndexPage1");