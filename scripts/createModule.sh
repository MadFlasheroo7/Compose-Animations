#!/bin/bash

read -rp "Enter Name of the module: " module_name

if [[ -z "$module_name" ]]; then
  echo "Error: Module cannot be empty."
  exit 1
fi

if [ -d "$module_name" ]; then
  echo "Error: Module already exists."
  exit 1
fi

read -rp "Enter package ID: $(printf "\nKindly Follow this format - 'pro.jayeshseth.animations.<animation/module>' \n")" package_id

if [[ -z "$package_id" ]]; then
  echo "Error: Package ID cannot be empty."
  exit 1
fi

base_dir="../features"
root_settings_gradle="../settings.gradle.kts"
final_module_structure="$base_dir/$module_name/src/main/kotlin/$(echo "$package_id" | tr '.' '/')"

echo "Generating Module..."

sub_dirs=("animations" "screens" "components")

for dir in "${sub_dirs[@]}"; do
  mkdir -p "$final_module_structure/$dir"
done

sed "s/package_id/$package_id/g" ../base/build.gradle.kts > "$base_dir/$module_name/build.gradle.kts"
touch "$base_dir/$module_name/.gitignore" && cp "../base/.gitignore" "$base_dir/$module_name/.gitignore"
touch "$base_dir/$module_name/proguard-rules.pro" && cp "../base/proguard-rules.pro" "$base_dir/$module_name/proguard-rules.pro"

include_line="include(\":features:$module_name\")"

echo "$include_line" >> "$root_settings_gradle"

echo "!! Summary !!"
echo "Created $module_name at $final_module_structure"
echo "with sub dir - ${sub_dirs[*]}"
echo "NOTE: !! Remember to sync android studio !!"