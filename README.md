**River's Keep Inventory**

Customize what exactly is dropped on death, for those who don't want to lose everything but want some consequences.

This is my first plugin!

It includes a config file that allows you to configure what is lost by players on death:

```
# whether or not slots 0 - 8 should be kept, or in other words, the items in your hot bar
keep-hotbar: true
# whether or not slots 9 - 35 should be kept, or in other words, the items not in your hot bar or equipment slots
keep-inventory: false
# whether or not armor and off hand equipment should be kept
keep-armor: true
# whether or not xp should be kept
keep-xp: false
```

If you have the gamerule keepInventory set to true, this plugin will gracefully do nothing. When it is set to false, this config will be used to determine what is kept or lost.
