$(document).ready(function() {
        $('#WorkplanTableContainer').jtable({
            title : 'Робочі навчальні плани',
            editinline: { enable : true },
            actions : {
                listAction: 'workplans?action=list',
                createAction: 'workplans?action=create',
                updateAction: 'workplans?action=update',
                deleteAction: 'workplans?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    //list : false,
                    create : false,
                    edit : false        
                },
                curriculumSubjects: {
                    title: 'Предмети',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (data) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="./css/metro/list.png" title="Предмети" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#WorkplanTableContainer').jtable('openChildTable',
                                    $img.closest('tr'), //Parent row
                                    {
                                    title: data.record.denotation + ' - предмети',
                                    actions: {
                                        listAction: 'curriculum-subject?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'curriculum-subject?action=delete',
                                        updateAction: 'curriculum-subject?action=update',
                                        createAction: 'curriculum-subject?action=create'
                                    },
                                    fields: {
                                        id : {
                                            title : 'Ідентифікатор',
                                            key : true,
                                            list : false,
                                            create : false,
                                            edit : false        
                                        },
                                        curriculum: {
                                            type: 'hidden',
                                            defaultValue: data.record.id
                                        },
                                        cipher : {
                                            title : 'Шифр',
                                            edit : true
                                        },
                                        subject : {
                                            title : 'Предмет',
                                            options: 'subjects?action=dropdownlist',
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
                diplomaPreparation: {
                    title: 'Підготовка дипломного проекту',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (data) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="./css/metro/list.png" title="Підготовка дипломного проекту" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#WorkplanTableContainer').jtable('openChildTable',
                                    $img.closest('tr'), //Parent row
                                    {
                                    title: data.record.denotation + ' - підготовка дипломного проекту',
                                    actions: {
                                        listAction: 'diploma-preparation?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'diploma-preparation?action=delete',
                                        updateAction: 'diploma-preparation?action=update',
                                        createAction: 'diploma-preparation?action=create'
                                    },
                                    fields: {
                                        id : {
                                            title : 'Ідентифікатор',
                                            key : true,
                                            list : false,
                                            create : false,
                                            edit : false        
                                        },
                                        workplan: {
                                            type: 'hidden',
                                            defaultValue: data.record.id
                                        },
                                        workType : {
                                            title : 'Вид роботи',
                                            options: 'work-type?action=dropdownlist',
                                            edit : true
                                        },
                                        norm : {
                                            title : 'Норма в годинах на 1 студента',                                            
                                            edit : true
                                        },
                                        department : {
                                            title : 'Кафедра',
                                            options: 'department?action=dropdownlist',
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
                },
                curriculum : {
                    title : 'Навчальний план',
                    options: 'curriculum?action=dropdownlist',
                    edit : true
                },
                practice : {
                    title : 'Практика',
                    options: 'practice?action=dropdownlist',
                    edit : true
                },
                stateCertification : {
                    title : 'Державна атестація',
                    options: 'state-certification?action=dropdownlist',
                    edit : true
                }
            }
        });
        $('#WorkplanTableContainer').jtable('load');
});