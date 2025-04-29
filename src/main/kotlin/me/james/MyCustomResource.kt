package me.james

import io.fabric8.generator.annotation.Required
import io.fabric8.kubernetes.api.model.Condition
import io.fabric8.kubernetes.client.CustomResource
import io.fabric8.kubernetes.model.annotation.Group
import io.fabric8.kubernetes.model.annotation.Version

@Group("me.james")
@Version("v1alpha1")
class MyCustomResource : CustomResource<MySpec, MyStatus>()

enum class MyEnum {
    VALUE1,
    VALUE2
}

data class MySpec(
    @Required
    val field1: String,
    val field2: MyEnum = MyEnum.VALUE1,
)

data class MyStatus(
    val conditions: List<Condition>
)
