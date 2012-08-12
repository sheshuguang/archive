
var authorityCommon = new us.archive.Authority();

$(function() {
	$('#orgtree')
			.tree(
					{
						checkbox : false,
						url : 'orgtreeAction.action?nodeId=0',
						onBeforeExpand : function(node, param) {
							$('#orgtree').tree('options').url = "orgtreeAction.action?nodeId="
									+ node.id;// change the url
						},
						onSelect : function(node) {
							if (node.id != '1') {
								authorityCommon.showAuthorityList(node);
							}

						}
					});
});
