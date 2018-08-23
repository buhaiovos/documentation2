$(document).ready(function() {
        $('#GroupTableContainer').jtable({
            title : 'Академічні групи',
            editinline: { enable : true },
            actions : {
                listAction : 'AcademicGroupController?action=list',
                createAction : 'AcademicGroupController?action=create',
                updateAction : 'AcademicGroupController?action=update',
                deleteAction : 'AcademicGroupController?action=delete'
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
                    options: 'SpecializationController?action=dropdownlist',
                    edit : true                                    
                },
                qualification : {
                    title : 'Освітньо-кваліфікаційний рівень',
                    options: 'QualificationController?action=dropdownlist',
                    edit : true
                },
                educationForm : {
                    title : 'Форма навчання',
                    options: 'EducationFormController?action=dropdownlist',
                    edit : true
                },
                workplan : {
                    title : 'Робочий план',
                    options: 'WorkplanController?action=dropdownlist',
                    edit : true
                }
            }
        });
        $('#GroupTableContainer').jtable('load');
});