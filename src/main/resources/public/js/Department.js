$(document).ready(function() {
        $('#DepartmentTableContainer').jtable({
            title : 'Кафедри',
            editinline: { enable : true },
            actions : {
                listAction: 'department?action=list',
                createAction: 'department?action=create',
                updateAction: 'department?action=update',
                deleteAction: 'department?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                specializations: {
                    title: 'Спеціалізації',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (data) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="./css/metro/list.png" title="Спеціалізації" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#DepartmentTableContainer').jtable('openChildTable',
                                    $img.closest('tr'), //Parent row
                                    {
                                    title: data.record.denotation + ' - спеціалізації',
                                    actions: {
                                        listAction: 'specialization?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'specialization?action=delete',
                                        updateAction: 'specialization?action=update',
                                        createAction: 'specialization?action=create'
                                    },
                                    fields: {
                                        id : {
                                            title : 'Ідентифікатор',
                                            key : true,
                                            list : false,
                                            create : false,
                                            edit : false        
                                        },
                                        department: {
                                            type: 'hidden',
                                            defaultValue: data.record.id
                                        },
                                        denotation : {
                                            title : 'Назва',
                                            edit : true
                                        }
                                    }
                                }, function (data) { //opened handler
                                    data.childTable.jtable('load');
                                });
                        });
                        //Return image to show on the person row
                        return $img;
                    }
                },
                denotation : {
                    title : 'Назва',
                    edit : true
                }
            }
        });
        $('#DepartmentTableContainer').jtable('load');
});