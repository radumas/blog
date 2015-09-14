# Presentations
A folder containing presentations made during my internship using [reveal.js](https://github.com/hakimel/reveal.js).

## Adding reveal.js as a submodule

I edited `.gitmodules` and added the following lines:
```bash
[submodule "presentations"]
        path = presentations
        url = https://github.com/hakimel/reveal.js.git
```
And then ran `git submodule sync` ([SO answer](http://stackoverflow.com/a/10453898/4047679)). This way, in theory, I can pull in new changes to the reveal.js platform.

## Adding a page to host presentations  

I created an `index.md` for this folder and then added YAML frontmatter for Jekyll to make an index page for this folder. It'd be nice to make a loop on it so that it automatically includes new presentations... but that might be rather tricky. I also added a new icon to the [menu bar](https://github.com/radumas/coppe-ltc/_includes/header.html) to make navigating to the presentations easier. 