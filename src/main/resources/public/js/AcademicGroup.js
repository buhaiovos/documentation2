$(document).ready(function() {
        $('#GroupTableContainer').jtable({
            title : 'Академічні групи',
            editinline: { enable : true },
            actions : {
                listAction: 'v2/academic-groups?action=list',
                createAction: 'v2/academic-groups?action=create',
                updateAction: 'v2/academic-groups?action=update',
                deleteAction: 'v2/academic-groups?action=delete'
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
                    options: 'v2/specializations?action=dropdown',
                    edit : true                                    
                },
                qualification : {
                    title : 'Освітньо-кваліфікаційний рівень',
                    options: 'v2/qualifications?action=dropdown',
                    edit : true
                },
                educationForm : {
                    title : 'Форма навчання',
                    options: 'v2/education-forms?action=dropdown',
                    edit : true
                },
                workplan : {
                    title : 'Робочий план',
                    options: 'v2/working-plans?action=dropdown',
                    edit : true
                }
            }
        });
        $('#GroupTableContainer').jtable('load');
});
