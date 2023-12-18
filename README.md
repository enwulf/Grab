# Minecraft Grab

Minecraft plugin for grabbing entities like cs 1.6

#### 🎆 Features:
<ul>
  <li><strong>Right-Click hold system</strong></li>
  <li><strong>Editable sidebar menu</strong></li>
  <li>Grabbing any living entities</li>
</ul>


#### Commands
- /grab reload - reload plugin
- /grab item - get item for grabbing


#### Permissions
- grab.use - for all user commands & use



# Configuration
```yaml
menu:
  font: 'Ubuntu Mono' # font must exist in the system
  title: 'Grab menu'
  lines:
    - '2. Kill'
    - '3. Rotate Screen'
    - '4. Push'
    - '5. Burn'
    - '6. Kick'
    - '7. Clear inventory'


# Available actions: KILL, ROTATE_SCREEN, PUSH, BURN, KICK, CLEAR_INVENTORY
# Format: slot_number: action
custom-slots:
  2: KILL
  3: ROTATE_SCREEN
  4: PUSH
  5: BURN
  6: KICK
  7: CLEAR_INVENTORY
```
