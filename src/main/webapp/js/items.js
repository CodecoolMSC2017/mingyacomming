function Item(name, quantity) {
  this.name = name;
  this.quantity = quantity;
  this.imageUrl = `url(images/${name}.png)`;

  this.getElement = function() {
    const itemE = CreateElement("item");
    itemE.setAttribute("name", this.name);
    itemE.setAttribute("quantity", this.quantity);
    itemE.style.backgroundImage = this.imageUrl;

    return itemE;
  }
}

function init() {
}

function switchToItemsPage() {
  visibilityOfPages("none");
  GetElement("#items_page").style.display = "block";
  loadItems();
}

function loadItems() {
  new Request("GET", "/items", null, onLoadItems);
}

function onLoadItems() {
  const items = JSON.parse(this.responseText);
  
  const itemsE = GetElement("#inventory");
  itemsE.textContent = null;

  items.forEach(item => {
    itemsE.appendChild(
      new Item(item.name, item.quantity).getElement()
    );
  });
}

document.addEventListener("DOMContentLoaded", init);