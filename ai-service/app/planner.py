from app.models import UISpecification, UIPage, UIComponent

class AIPlanner:

    def generate(self, user_description, entities, previous_specification=None):
        print("User description:", user_description)
        print("Entities:", entities)

        specification = UISpecification(
            name="Employee Management",
            layout="sidebar",
            pages=[
                UIPage(
                    name="Dashboard",
                    view="dashboard",
                    components=[
                        UIComponent(
                            type="metric",
                            entity="Employee",
                            features=["count"]
                        )
                    ]
                ),

                UIPage(
                    name="Employees",
                    entity="Employee",
                    view="crud",
                    components=[
                        UIComponent(
                            type="table",
                            entity="Employee",
                            features=[
                                "create",
                                "edit",
                                "delete",
                                "search"
                            ]
                        )
                    ]
                )
            ]
        )

        return specification