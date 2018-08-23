package edu.cad.documentelements.k3columns;

import edu.cad.utils.documentutils.ColumnTokenStringSplitter;

public interface K3WPColumnTokens {
    String TOKEN_BEGINNING = 
            ColumnTokenStringSplitter.K3_WP_TOKEN_BEGINNING;
    
    String FULL_TITLE   = TOKEN_BEGINNING + "fulltitle";
    String SEM_HOURS    = TOKEN_BEGINNING + "semhours";
    String LECTIONS     = TOKEN_BEGINNING + "lection";
    String PRACTICE     = TOKEN_BEGINNING + "practice";
    String LABS         = TOKEN_BEGINNING + "lab";
    String INDIVIDUALS  = TOKEN_BEGINNING + "individual";
    
    String EXAMS            = TOKEN_BEGINNING + "exam";
    String CREDITS          = TOKEN_BEGINNING + "credit";
    String CONTROL_WORKS    = TOKEN_BEGINNING + "contrwork";
    String COURSE_PROJS     = TOKEN_BEGINNING + "courseproj";
    String COURSE_WORKS     = TOKEN_BEGINNING + "coursework";
    String RGRS             = TOKEN_BEGINNING + "rgr";
    String DKR              = TOKEN_BEGINNING + "dkr";
    String REFERATS         = TOKEN_BEGINNING + "referat";
    
    String AC_GROUPS       = TOKEN_BEGINNING + "acgroup";
    String AC_GROUPS_OTHER = TOKEN_BEGINNING + "acgroupother";
    String SUBGR_PRACT     = TOKEN_BEGINNING + "subgrpract";
    String SUBGR_LABS      = TOKEN_BEGINNING + "subgrlab";
     
    String BUDG_GR_BUDG_STUD = TOKEN_BEGINNING + "groupbstb";
    String BUDG_GR_CONT_STUD = TOKEN_BEGINNING + "groupbstc";
    String CONT_GR_BUDG_STUD = TOKEN_BEGINNING + "groupcstb";
    String CONT_GR_CONT_STUD = TOKEN_BEGINNING + "groupcstc";
    
    String STREAM = TOKEN_BEGINNING + "stream";
   
}
