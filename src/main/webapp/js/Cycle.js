$(document).ready(function() {
        $('#CycleTableContainer').jtable({
            title : 'Розділи предметів',
            editinline: { enable : true },
            actions : {
                listAction : 'CycleController?action=list',
                createAction : 'CycleController?action=create',
                updateAction : 'CycleController?action=update',
                deleteAction : 'CycleController?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                sections: {
                    title: 'Підрозділи',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (data) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="./css/metro/list.png" title="Підрозділи" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#CycleTableContainer').jtable('openChildTable',
                                    $img.closest('tr'), //Parent row
                                    {
                                    title: data.record.denotation + ' - підрозділи',
                                    actions: {
                                        listAction: 'SectionController?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'SectionController?action=delete',
                                        updateAction: 'SectionController?action=update',
                                        createAction: 'SectionController?action=create'
                                    },
                                    fields: {
                                        id : {
                                            title : 'Ідентифікатор',
                                            key : true,
                                            //list : false,
                                            create : false,
                                            edit : false        
                                        },
                                        cycle: {
                                            type: 'hidden',
                                            defaultValue: data.record.id
                                        },
                                        denotation : {
                                            title : 'Назва',
                                            edit : true
                                        },
                                        optional : {
                                            title : 'За вибором студентів',
                                            type: 'checkbox',
                                            edit : true,
                                            values: { 'false': 'Ні', 'true': 'Так' },
                                            defaultValue: 'false'
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
        $('#CycleTableContainer').jtable('load');
});