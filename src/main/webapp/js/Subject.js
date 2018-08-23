$(document).ready(function() {
        $('#SubjectTableContainer').jtable({
            title : 'Предмети',
            editinline: { enable : true },
            actions : {
                listAction : 'SubjectDictionaryController?action=list',
                createAction : 'SubjectDictionaryController?action=create',
                updateAction : 'SubjectDictionaryController?action=update',
                deleteAction : 'SubjectDictionaryController?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                academicSubjects: {
                    title: 'Предмети',
                    sorting: false,
                    edit: false,
                    create: false,
                    display: function (data) {
                        //Create an image that will be used to open child table
                        var $img = $('<img src="./css/metro/list.png" title="Предмети" />');
                        //Open child table when user clicks the image
                        $img.click(function () {
                            $('#SubjectTableContainer').jtable('openChildTable',
                                    $img.closest('tr'), //Parent row
                                    {
                                    title: data.record.denotation + ' - предмети',
                                    actions: {
                                        listAction: 'SubjectController?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'SubjectController?action=delete',
                                        updateAction: 'SubjectController?action=update',
                                        createAction: 'SubjectController?action=create'
                                    },
                                    fields: {
                                        id : {
                                            title : 'Ідентифікатор',
                                            key : true,
                                            list : false,
                                            create : false,
                                            edit : false        
                                        },
                                        controls: {
                                            title: 'Контрольні заходи',
                                            sorting: false,
                                            edit: false,
                                            create: false,
                                            display: function (childData) {
                                                //Create an image that will be used to open child table
                                                var $childImg = $('<img src="./css/metro/list.png" title="Контрольні заходи" />');
                                                //Open child table when user clicks the image
                                                $childImg.click(function () {
                                                    $('#SubjectTableContainer').jtable('openChildTable',
                                                            $childImg.closest('tr'), //Parent row
                                                            {
                                                            title: data.record.denotation + ' - контрольні заходи',
                                                            actions: {
                                                                listAction: 'ControlController?action=dependencylist&id=' + childData.record.id,
                                                                deleteAction: 'ControlController?action=delete',
                                                                updateAction: 'ControlController?action=update',
                                                                createAction: 'ControlController?action=create'
                                                            },
                                                            fields: {
                                                                id : {
                                                                    title : 'Ідентифікатор',
                                                                    key : true,
                                                                    list : false,
                                                                    create : false,
                                                                    edit : false        
                                                                },
                                                                subject: {
                                                                    type: 'hidden',
                                                                    defaultValue: childData.record.id
                                                                },
                                                                type : {
                                                                    title : 'Тип контрольного заходу',
                                                                    options: 'ControlDictionaryController?action=dropdownlist',
                                                                    edit : true
                                                                },
                                                                semester : {
                                                                    title : 'Семестр',
                                                                    edit : true
                                                                }
                                                            }
                                                        }, function (childData) { //opened handler
                                                            childData.childTable.jtable('load');
                                                    });
                                                });
                                                //Return image to show on the person row
                                                return $childImg;
                                            }
                                        },
                                        subject : {
                                            type: 'hidden',
                                            defaultValue: data.record.id
                                        },
                                        semester : {
                                            title : 'Семестр',
                                            edit : true
                                        },
                                        semestersDuration : {
                                            title : 'Тривалість у семестрах',
                                            edit : true
                                        },
                                        ects : {
                                            title : 'ECTS',
                                            edit : true
                                        },
                                        lections : {
                                            title : 'Лекції',
                                            edit : true
                                        },
                                        labs : {
                                            title : 'Лабораторні',
                                            edit : true
                                        },
                                        practices : {
                                            title : 'Практичні',
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
                department : {
                    title : 'Кафедра',
                    options: 'DepartmentController?action=dropdownlist',
                    edit : true
                },
                superSubject : {
                    title : 'Загальний предмет',
                    options: 'SubjectDictionaryController?action=dropdownlist',
                    edit : true,
                    defaultValue: ''
                },
                curriculumSection : {
                    title : 'Розділ у навчальному плані',
                    options: 'SectionController?action=dropdownlist',
                    edit : true
                },
                workplanSection : {
                    title : 'Розділ у робочому навчальному плані',
                    options: 'SectionController?action=dropdownlist',
                    edit : true,
                    defaultValue: ''
                },
                type : {
                    title : 'Тип',
                    options: 'SubjectTypeController?action=dropdownlist',
                    edit : true
                }
            }
        });
        $('#SubjectTableContainer').jtable('load');
});