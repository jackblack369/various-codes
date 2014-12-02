Exemple 4.16 : rmid.policy

grant
{
   permission com.sun.rmi.rmid.ExecPermission 
      "${java.home}${/}bin${/}java";
   permission com.sun.rmi.rmid.ExecOptionPermission
      "-Djava.security.policy=*";
};

