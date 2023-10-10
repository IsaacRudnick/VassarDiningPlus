import requests
import re
import json

URL = "https://vassar.cafebonappetit.com/cafe/gordon/"
page = requests.get(URL)

# print(page.text)
result = re.search("Bamco.menu_items = (.*);", page.text)
scraped_dishes = json.loads(result.group(1))

menus_dishes = {}

for id, dish_info in scraped_dishes.items():
    # Get the station name and remove the emphasis tags from the data
    menu = dish_info["station"].replace("<strong>", "").replace("</strong>", "")
    name = dish_info["label"]
    description = dish_info["description"]
    # If restrictions is empty list, no restrictions
    # Otherwise, get the values of the restrictions and store them as list
    restrictions = (
        [*dish_info["cor_icon"].values()] if (dish_info["cor_icon"] != []) else []
    )
    print(f"{menu = }\n\t{name = }\n\t{description = }\n\t{restrictions = }\n")

    # If this is the first time we've seen this menu, add it to the dict
    if menu not in menus_dishes:
        menus_dishes[menu] = {}

    # Add the dish to the menu dict with the id as the key
    menus_dishes[menu][id] = {
        "name": name.title(),
        "description": description,
        "restrictions": restrictions,
    }

with open("menus_dishes.json", "w") as f:
    json.dump(menus_dishes, f, indent=4)

print("Done!")
