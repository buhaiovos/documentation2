package edu.cad.documentelements.k3columns;

import edu.cad.utils.documentutils.ColumnTokenStringSplitter;

final class K3WPColumnTokens {
    private static final String TOKEN_BEGINNING = ColumnTokenStringSplitter.K3_WP_TOKEN_BEGINNING;

    static final String ORDER_NUM = TOKEN_BEGINNING + "sem";
    static final String FULL_TITLE = TOKEN_BEGINNING + "fulltitle";
    static final String SEM_HOURS = TOKEN_BEGINNING + "semhours";
    static final String LECTURES = TOKEN_BEGINNING + "lectures";
    static final String PRACTICE = TOKEN_BEGINNING + "practice";
    static final String LABS = TOKEN_BEGINNING + "lab";
    static final String INDIVIDUALS = TOKEN_BEGINNING + "individuals";

    static final String EXAMS = TOKEN_BEGINNING + "exam";
    static final String CREDITS = TOKEN_BEGINNING + "credit";
    static final String CONTROL_WORKS = TOKEN_BEGINNING + "contrwork";
    static final String COURSE_PROJS = TOKEN_BEGINNING + "courseproj";
    static final String COURSE_WORKS = TOKEN_BEGINNING + "coursework";
    static final String RGRS = TOKEN_BEGINNING + "rgr";
    static final String DKR = TOKEN_BEGINNING + "dkr";
    static final String REFERATS = TOKEN_BEGINNING + "referat";

    static final String AC_GROUPS = TOKEN_BEGINNING + "acgroup";
    static final String AC_GROUPS_OTHER = TOKEN_BEGINNING + "acgroupother";
    static final String SUBGR_PRACT = TOKEN_BEGINNING + "subgrpract";
    static final String SUBGR_LABS = TOKEN_BEGINNING + "subgrlab";

    static final String BUDG_GR_BUDG_STUD = TOKEN_BEGINNING + "groupbstb";
    static final String BUDG_GR_CONT_STUD = TOKEN_BEGINNING + "groupbstc";
    static final String CONT_GR_BUDG_STUD = TOKEN_BEGINNING + "groupcstb";
    static final String CONT_GR_CONT_STUD = TOKEN_BEGINNING + "groupcstc";

    static final String STREAM = TOKEN_BEGINNING + "stream";

}
