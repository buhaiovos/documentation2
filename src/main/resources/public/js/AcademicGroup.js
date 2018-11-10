$(document).ready(function() {
        $('#GroupTableContainer').jtable({
            title : 'Академічні групи',
            editinline: { enable : true },
            actions : {
                listAction: 'academic-group?action=list',
                createAction: 'academic-group?action=create',
                updateAction: 'academic-group?action=update',
                deleteAction: 'academic-group?action=delete'
            },
            fields : {
                id : {
                    title : 'Ідентифікатор',
                    key : true,
                    list : false,
                    create : false,
                    edit : false        
                },
                cipher : {
                    title : 'Шифр',
                    edit : true
                },
                budgetaryStudents : {
                    title : 'Студенти бюджетної форми',
                    edit : true
                },
                contractStudents : {
                    title : 'Студенти контрактної форми',
                    edit : true
                },
                startYear : {
                    title : 'Рік набору',
                    edit : true
                },
                specialization : {
                    title : 'Спеціалізація',
                    options: 'specialization?action=dropdownlist',
                    edit : true                                    
                },
                qualification : {
                    title : 'Освітньо-кваліфікаційний рівень',
                    options: 'qualification?action=dropdownlist',
                    edit : true
                },
                educationForm : {
                    title : 'Форма навчання',
                    options: 'form-of-education?action=dropdownlist',
                    edit : true
                },
                workplan : {
                    title : 'Робочий план',
                    options: 'workplans?action=dropdownlist',
                    edit : true
                }
            }
        });
        $('#GroupTableContainer').jtable('load');
});