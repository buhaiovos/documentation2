$(document).ready(function() {
        $('#SubjectTableContainer').jtable({
            title : 'Предмети',
            editinline: { enable : true },
            actions : {
                listAction: 'subject-headers?action=list',
                createAction: 'subject-headers?action=create',
                updateAction: 'subject-headers?action=update',
                deleteAction: 'subject-headers?action=delete'
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
                                        listAction: 'subject-info?action=dependencylist&id=' + data.record.id,
                                        deleteAction: 'subject-info?action=delete',
                                        updateAction: 'subject-info?action=update',
                                        createAction: 'subject-info?action=create'
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
                                                                listAction: 'control?action=dependencylist&id=' + childData.record.id,
                                                                deleteAction: 'control?action=delete',
                                                                updateAction: 'control?action=update',
                                                                createAction: 'control?action=create'
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
                                                                    options: 'control-dictionary?action=dropdownlist',
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
                                        lectures: {
                                            title : 'Лекції',
                                            edit : true
                                        },
                                        actual_lectures: {
                                            title: 'Факт. лекції',
                                            edit: true
                                        },
                                        labs : {
                                            title : 'Лабораторні',
                                            edit : true
                                        },
                                        actual_labs: {
                                            title: 'Факт. лаб.',
                                            edit: true
                                        },
                                        practices : {
                                            title : 'Практичні',
                                            edit : true
                                        },
                                        actual_practices: {
                                            title: 'Факт. практ.',
                                            edit: true
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
                    options: 'department?action=dropdownlist',
                    edit : true
                },
                superSubject : {
                    title : 'Загальний предмет',
                    options: 'subject-headers?action=dropdownlist',
                    edit : true,
                    defaultValue: ''
                },
                curriculumSection : {
                    title : 'Розділ у навчальному плані',
                    options: 'section?action=dropdownlist',
                    edit : true
                },
                workplanSection : {
                    title : 'Розділ у робочому навчальному плані',
                    options: 'section?action=dropdownlist',
                    edit : true,
                    defaultValue: ''
                },
                type : {
                    title : 'Тип',
                    options: 'subject-types?action=dropdownlist',
                    edit : true
                }
            }
        });
        $('#SubjectTableContainer').jtable('load');
});