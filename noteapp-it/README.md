# setup

### admin cluster user

oc create user admin

oc adm policy add-cluster-role-to-user cluster-admin admin

oc create identity anypassword:admin

oc create useridentitymapping anypassword:admin admin
