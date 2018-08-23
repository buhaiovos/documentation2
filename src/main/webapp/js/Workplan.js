$(document).ready(function() {
        $('#WorkplanTableContainer').jtable({
            title : 'Робочі навчальні плани',
            editinline: { enable : true },
            actions : {
                listAction : 'WorkplanController?action=list',
                createAction : 'WorkplanController?action=create',
                updateAction : 'WorkplanController?action=update',
                deleteAction : 'WorkplanController?action=delete'
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
                                        listAction: 'CurriculumSubjectController?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'CurriculumSubjectController?action=delete',
                                        updateAction: 'CurriculumSubjectController?action=update',
                                        createAction: 'CurriculumSubjectController?action=create'
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
                                            options: 'SubjectController?action=dropdownlist',
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
                                        listAction: 'DiplomaPreparationController?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'DiplomaPreparationController?action=delete',
                                        updateAction: 'DiplomaPreparationController?action=update',
                                        createAction: 'DiplomaPreparationController?action=create'
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
                                            options: 'WorkTypeController?action=dropdownlist',
                                            edit : true
                                        },
                                        norm : {
                                            title : 'Норма в годинах на 1 студента',                                            
                                            edit : true
                                        },
                                        department : {
                                            title : 'Кафедра',
                                            options: 'DepartmentController?action=dropdownlist',
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
                    options: 'CurriculumController?action=dropdownlist',
                    edit : true
                },
                practice : {
                    title : 'Практика',
                    options: 'PracticeController?action=dropdownlist',
                    edit : true
                },
                stateCertification : {
                    title : 'Державна атестація',
                    options: 'StateCertificationController?action=dropdownlist',
                    edit : true
                }
            }
        });
        $('#WorkplanTableContainer').jtable('load');
});