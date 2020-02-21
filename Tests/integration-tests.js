const frisby = require('frisby');

describe("Check health",function(){
    it("Check if schema-manager1 service is alive",function(done){
        return frisby.get("http://localhost:8080/monitor")
            .expect('status',200)
            .expect('bodyContains',"alive")
            .done(done);
    });

    it("Check if schema-manager2 service is alive",function(done){
        return frisby.get("http://localhost:8081/monitor")
            .expect('status',200)
            .expect('bodyContains',"alive")
            .done(done);
    });
});


describe("Check creating and reading schema for a site in redis",function(){
    it("Add schema for site new-site",function(done){
        return frisby.post("http://localhost:8080/site/new-site/schema", {
            siteKey: "new-site",
            fields: [
                {
                    dataType: "Text",
                    autoSuggest: false,
                    multiValue: true,
                    fieldName: "Occasion"
                },
                {
                    dataType: "Text",
                    autoSuggest: false,
                    multiValue: true,
                    fieldName: "fabric"
                },
                {
                    dataType: "Number",
                    autoSuggest: false,
                    multiValue: false,
                    fieldName: "qty"
                },
                {
                    dataType: "date",
                    fieldName: false,
                    multiValue: false,
                    fieldName: "createdAt"
                },
                {
                    dataType: "date",
                    fieldName: false,
                    multiValue: false,
                    fieldName: "updatedAt"
                },
                {
                    dataType: "bool",
                    fieldName: false,
                    multiValue: false,
                    fieldName: "isFeatured"
                }
            ]
        })
            .expect('status',200)
            .expect('bodyContains',"success")
            .done(done);
    });

    it("Get schema for site",function(done){
        frisby.get("http://localhost:8080/new-site/schema")
            .expect('status',200)
            .expect('bodyContains',"new-site")
            .expect('bodyContains',"Occasion")
            .done(done);
    });

    it("Get field properties for fieldname",function(done){
        frisby.get("http://localhost:8080/site/new-site/field/fabric")
            .expect('status',200)
            .expect('bodyContains',"fabric")
            .expect('bodyContains',"Text")
            .done(done);
    });

    it("Update field properties for fieldname", function(done){
        frisby.post("http://localhost8080/site/new-site/field/occasion", {
            dataType: "Text",
            autoSuggest: false,
            multiValue: false,
            fieldName: "Occasion"
        })
            .expect('status',200)
            .expect('bodyContains',"success")
            .done(done)
    });

    it("Delete Site ", function(done){
        frisby.delete("http://localhost:8080/site/new-site")
            .expect('status',200)
            .expect('bodyContains',success)
            .done(done);
    });
})

describe("Check creating and reading schema for a site in mongo",function(){
    it("Add schema for site new-site",function(done){
        return frisby.post("http://localhost:8081/site/new-site/schema", {
            siteKey: "new-site",
            fields: [
                {
                    dataType: "Text",
                    autoSuggest: false,
                    multiValue: true,
                    fieldName: "Occasion"
                },
                {
                    dataType: "Text",
                    autoSuggest: false,
                    multiValue: true,
                    fieldName: "fabric"
                },
                {
                    dataType: "Number",
                    autoSuggest: false,
                    multiValue: false,
                    fieldName: "qty"
                },
                {
                    dataType: "date",
                    fieldName: false,
                    multiValue: false,
                    fieldName: "createdAt"
                },
                {
                    dataType: "date",
                    fieldName: false,
                    multiValue: false,
                    fieldName: "updatedAt"
                },
                {
                    dataType: "bool",
                    fieldName: false,
                    multiValue: false,
                    fieldName: "isFeatured"
                }
            ]
        })
            .expect('status',200)
            .expect('bodyContains',"success")
            .done(done);
    });

    it("Get schema for site",function(done){
        frisby.get("http://localhost:8081/new-site/schema")
            .expect('status',200)
            .expect('bodyContains',"new-site")
            .expect('bodyContains',"Occasion")
            .done(done);
    });

    it("Get field properties for fieldname",function(done){
        frisby.get("http://localhost:8081/site/new-site/field/fabric")
            .expect('status',200)
            .expect('bodyContains',"fabric")
            .expect('bodyContains',"Text")
            .done(done);
    });

    it("Update field properties for fieldname", function(done){
        frisby.post("http://localhost8081/site/new-site/field/occasion", {
            dataType: "Text",
            autoSuggest: false,
            multiValue: false,
            fieldName: "Occasion"
        })
            .expect('status',200)
            .expect('bodyContains',"success")
            .done(done)
    });

    it("Delete Site ", function(done){
        frisby.delete("http://localhost:8081/site/new-site")
            .expect('status',200)
            .expect('bodyContains',success)
            .done(done);
    });
})



