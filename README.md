# Exercise_Accounts

<p>I have created this application with spring boot + maven + jap + junit</p>
<p>I have used MySql Workbench to make a database.</p>
<p>I have put Treasury values 0 or 1 to know if the account is treasury (value = 1) or it is not treasury (value = 0)</p>
<p>I have used Thymeleaf (a Java library) to attach hmtl files to controller.</p>
<p>I have used Mockito to make JUnit tests.</p>

<h3>How to work</h3>
<ul>I put 3 view:
  <li>Information and delete account</li>
  <li>Transfer money into accounts</li>
  <li>Create account</li>
</ul>

<p>Into send money view, only senders with treasury could have a negative account. If sender is not treasury appear an error message</p>
<p>Into create account view, you can choose currency(&euro;, &dollar;, &pound;) and if a treasury account or not</p>

<p>You could do this running the server and access with a browser also you could do with calls to the API end points too</p>
<ul>API end points:
  <li>accountsPage</li>
  <li>sendMoneyPage</li>
  <li>createAccount</li>
</ul>
