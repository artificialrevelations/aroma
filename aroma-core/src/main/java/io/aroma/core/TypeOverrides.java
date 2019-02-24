package io.aroma.core;

abstract class TypeOverrides {
//    public abstract TypeOverrides append(final TypeOverrides overrides);

    public static final class None extends TypeOverrides {
        public None() {
        }
    }

    public static final class Both extends TypeOverrides {
        private final Aroma.MapTypes mapType;
        private final Aroma.CollectionTypes collectionType;

        public Both(final Aroma.MapTypes mapType,
                    final Aroma.CollectionTypes collectionType) {
            this.mapType = mapType;
            this.collectionType = collectionType;
        }
    }

    public static final class MapType extends TypeOverrides {
        private final Aroma.MapTypes mapType;

        public MapType(final Aroma.MapTypes mapType) {
            this.mapType = mapType;
        }
    }

    public static final class CollectionType extends TypeOverrides {
        private final Aroma.CollectionTypes collectionType;

        public CollectionType(final Aroma.CollectionTypes collectionType) {
            this.collectionType = collectionType;
        }
    }
}
