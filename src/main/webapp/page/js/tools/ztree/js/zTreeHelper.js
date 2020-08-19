var zTreeHelper = {
    createNew: function () {
        var hlp = {};

        hlp.treeObj = null;

        hlp.init = function (setting, data, treeID) {
            $("#" + treeID).empty();
            $.fn.zTree.init($("#" + treeID), setting, data);
            hlp.treeObj = $.fn.zTree.getZTreeObj(treeID);
            //return $.fn.zTree.getZTreeObj(treeID);
        }

        hlp.getCheckedNodes = function () {
            return hlp.treeObj.getCheckedNodes();
        }

        hlp.getSelectedNode = function () {
            return hlp.treeObj.getSelectedNode();
        }
        
        hlp.resetTree = function(){
        	hlp.treeObj.checkAllNodes(false);
        	hlp.treeObj.expandAll(false);
        }

        hlp.checkNodesByID = function (ids) {
            var arr = hlp.treeObj.getNodes()[0].children;
            for (var i = 0; i < arr.length; i++) {
                var node = arr[i];
                if (ids.replace("|" + node.id + "|", "") != ids) {
                    hlp.treeObj.checkNode(node, true, true);
                }
            }
        }

        return hlp;
    }
};

////sample data

////simple data  {id parentId name}
//var data = [
//    { id: 1, pId: 0, name: "BigT1", checked: true },
//    { id: 2, pId: 0, name: "BigT2" },
//    { id: 3, pId: 1, name: "BigT3" },
//    { id: 4, pId: 2, name: "BigT4" },
//    { id: 5, pId: 3, name: "BigT5" },
//    { id: 6, pId: 3, name: "BigT6" }
//];

////complex data
//var data2 = [
//    {
//        name: "BigT1", open: false,
//        children: [
//            {
//                name: "BigT2",
//                children: [
//                    { name: "BigT3" }
//                ]
//            }
//        ]
//    }
//]