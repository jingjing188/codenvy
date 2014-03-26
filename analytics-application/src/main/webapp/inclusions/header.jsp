<%-- 
 CODENVY CONFIDENTIAL
 ________________

 [2012] - [2014] Codenvy, S.A.
 All Rights Reserved.
 NOTICE: All information contained herein is, and remains
 the property of Codenvy S.A. and its suppliers,
 if any. The intellectual and technical concepts contained
 herein are proprietary to Codenvy S.A.
 and its suppliers and may be covered by U.S. and Foreign Patents,
 patents in process, and are protected by trade secret or copyright law.
 Dissemination of this information or reproduction of this material
 is strictly forbidden unless prior written permission is obtained
 from Codenvy S.A.. 
--%>

<link rel="stylesheet" type="text/css" href="/analytics/bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="/analytics/bootstrap/css/bootstrap-responsive.min.css"/>
<link rel="stylesheet" type="text/css" href="/analytics/css/styles.css"/>
<link rel="stylesheet" type="text/css" href="/analytics/css/single-column.css"/>
<link rel="stylesheet" type="text/css" href="/analytics/css/view.css"/>
<link rel="stylesheet" type="text/css" href="/analytics/css/database-table.css" />

<style type="text/css">
    body {
        padding-top: 60px;
    }

    .sidebar-nav {
        padding: 9px 0;
    }
</style>

<script type="text/javascript" src="/analytics/scripts/third-party/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="/analytics/bootstrap/js/bootstrap.js"></script>

<link rel="stylesheet" href="/analytics/scripts/third-party/jquery-ui-1.8.20/themes/base/minified/jquery-ui.min.css">
<script type="text/javascript"
        src="/analytics/scripts/third-party/jquery-ui-1.8.20/ui/minified/jquery-ui.min.js"></script>

<!-- DataTable plugin -->
<script type="text/javascript" src="/analytics/scripts/third-party/jquery.dataTables-1.9.4.min.js"></script>
<script>
	// setup default settings
	jQuery.extend(true, jQuery.fn.dataTable.defaults, {
	       "bPaginate": false,
	       "bLengthChange": false,
	       "bFilter": false,
	       "bSort": true,
	       "bInfo": false,
	       "bAutoWidth": false,
	       "oLanguage": {
	           "sEmptyTable": "<div class='system'>empty table</div>"
	       },      
	});

    // setup own classes for table headers
    jQuery(function() {
		jQuery.extend(jQuery.fn.dataTableExt.oStdClasses, {
		    "sSortAsc": "ascending",
		    "sSortDesc": "descending",
		    "sSortable": "unsorted",
		});
		

    });
</script>